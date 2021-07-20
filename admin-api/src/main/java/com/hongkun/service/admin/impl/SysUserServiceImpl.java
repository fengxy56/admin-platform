package com.hongkun.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.SysUser;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.admin.SysUserMapper;
import com.hongkun.query.admin.SysUserQuery;
import com.hongkun.query.admin.SysUserSaveQuery;
import com.hongkun.query.admin.SysUserUpdateSortQuery;
import com.hongkun.service.admin.SysUserService;
import com.hongkun.until.AESUtil;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.until.MD5Util;
import com.hongkun.until.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @ClassName SysUserServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/16 14:14
 */
@Slf4j
@Service
public class SysUserServiceImpl implements SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;


    //分页查询用户信息
    public IPage<SysUser> getSysUserList(SysUserQuery sysUserQuery){

        Page<SysUser> page = new Page<>(sysUserQuery.getPageNo(), sysUserQuery.getPageSize());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotBlank(sysUserQuery.getOrgId())) {
            queryWrapper.eq("orgId", sysUserQuery.getOrgId());
        }
        if (StringUtils.isNotBlank(sysUserQuery.getAccount())) {
            queryWrapper.eq("account", sysUserQuery.getAccount());
        }
        if (StringUtils.isNotBlank(sysUserQuery.getName())) {
            queryWrapper.like("name", sysUserQuery.getName());
        }
        queryWrapper.eq("status",sysUserQuery.getStatus());
        queryWrapper.orderByDesc("showOrder");
        IPage<SysUser> sysDictPage = sysUserMapper.selectPage(page, queryWrapper);

        return sysDictPage;
    }


    //保存或者修改用户
    @Transactional(value = "adminTransactionManager")
    public int saveSysUser(SysUserSaveQuery sysUserSaveQuery){

        int count = 0 ;

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveQuery,sysUser);


        QueryWrapper<SysUser> accountQueryWrapper = new QueryWrapper();
        accountQueryWrapper.eq("account",sysUser.getAccount());
        SysUser accountSysUser = sysUserMapper.selectOne(accountQueryWrapper);

        //数据库不存在账户,新增，否则修改
        if(accountSysUser == null){
            sysUser.setId(CodeGeneratedUtil.genUUID());

            if(StringUtils.isNotBlank(sysUser.getName())){
                sysUser.setFirstPinyin(PinyinUtil.getPinyinInitials(sysUser.getName()));//拼音s首字母
                sysUser.setFullPinyin(PinyinUtil.getPinyin(sysUser.getName(),""));//拼音全拼
                sysUser.setFullPinyinSpace(PinyinUtil.getPinyinBySeparator(sysUser.getName()," "));//拼音全拼,有空格
            }

            //校验密码，并设置
            if( StringUtils.isNotBlank(sysUser.getPassword())
                    && StringUtils.isNotBlank(sysUserSaveQuery.getSurePassword())
                    ){
                this.setUserPassword(sysUser,sysUserSaveQuery.getSurePassword());
            }else{
                throw new GlobalException(0,"密码或者确认密码不能为空");
            }


            QueryWrapper<SysUser> orgIdQueryWrapper = new QueryWrapper();
            orgIdQueryWrapper.eq("orgId",sysUser.getOrgId());
            orgIdQueryWrapper.eq("status",1);
            orgIdQueryWrapper.orderByDesc("showOrder").last("limit 1");
            SysUser orgIdSysUser = sysUserMapper.selectOne(orgIdQueryWrapper);
            if(orgIdSysUser !=null){
                sysUser.setShowOrder(orgIdSysUser.getShowOrder()+1);
            }else{
                sysUser.setShowOrder(1);
            }

            sysUser.setStatus(1);
            sysUser.setCreateTime(new Date());
            sysUser.setLastUpdateTime(new Date());
            count = sysUserMapper.insert(sysUser);
        }else{
            throw new GlobalException(0,"账户已经存在,不能添加!");
        }

        return count;
    }


    //修改用户
    @Transactional(value = "adminTransactionManager")
    @Override
    public int updateSysUser(SysUserSaveQuery sysUserSaveQuery){

        int count = 0 ;

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(sysUserSaveQuery,sysUser);


        QueryWrapper<SysUser> accountQueryWrapper = new QueryWrapper();
        accountQueryWrapper.eq("account",sysUser.getAccount());
        SysUser accountSysUser = sysUserMapper.selectOne(accountQueryWrapper);

        //数据库不存在账户,新增，否则修改
        if(accountSysUser != null){
            sysUser.setId(accountSysUser.getId());

            if(StringUtils.isNotBlank(sysUser.getName())){
                sysUser.setFirstPinyin(PinyinUtil.getPinyinInitials(sysUser.getName()));//拼音s首字母
                sysUser.setFullPinyin(PinyinUtil.getPinyin(sysUser.getName(),""));//拼音全拼
                sysUser.setFullPinyinSpace(PinyinUtil.getPinyinBySeparator(sysUser.getName()," "));//拼音全拼,有空格
            }
            //校验密码，并设置
            if( StringUtils.isNotBlank(sysUser.getPassword())
                    && StringUtils.isNotBlank(sysUserSaveQuery.getSurePassword())
                    ){
                this.setUserPassword(sysUser,sysUserSaveQuery.getSurePassword());
            }

            sysUser.setLastUpdateTime(new Date());
            count = sysUserMapper.updateById(sysUser);
        }else{
            throw new GlobalException(0,"账户不存在,不能修改!");
        }

        return count;
    }


    //校验密码，并设置
    public void setUserPassword(SysUser sysUser,String surePassword){

        if( StringUtils.isNotBlank(sysUser.getPassword())
                && StringUtils.isNotBlank(surePassword)
                && sysUser.getPassword().equals(surePassword)
                ){
            boolean flag = this.checkPassword(sysUser.getPassword());
            if(flag){
                try {
                    sysUser.setPassword(MD5Util.getMD5(sysUser.getPassword()+"123456"));
                    sysUser.setPasswordAec(AESUtil.encrypt(sysUser.getPassword(),"123456"));
                } catch (MD5Util.MD5Exception e) {
                    log.error("密码设置异常:"+e);
                    throw new GlobalException(0,"密码设置错误");
                }
            }else{
                throw new GlobalException(0, "密码长度为6到16位,必须同时包含大写字母、小写字母和数字三种规则");
            }
        }else{
            throw new GlobalException(0,"密码和确认密码不一致");
        }

        //return sysUser;
    }

    public static boolean checkPassword(String password){
        if (StringUtils.isNotBlank(password) && password.matches(".*[a-z]{1,}.*")
                && password.matches(".*[A-Z]{1,}.*") && password.matches(".*\\d{1,}.*")
                && password.length()>=6 && password.length()<=16) {
            return true;
        }
        return false;
    }


    //上移人员
    @Transactional(value = "adminTransactionManager")
    @Override
    public void moveUpSysUserSort(SysUserUpdateSortQuery sysUserUpdateSortQuery){

        int showOrder = sysUserUpdateSortQuery.getShowOrder();

        //查询上一条记录，
        QueryWrapper<SysUser> orgIdQueryWrapper = new QueryWrapper();
        orgIdQueryWrapper.eq("orgId",sysUserUpdateSortQuery.getOrgId());
        orgIdQueryWrapper.gt("showOrder",showOrder);//大于
        orgIdQueryWrapper.eq("status",1);
        orgIdQueryWrapper.orderByAsc("showOrder").last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(orgIdQueryWrapper);
        if(sysUser == null){
            throw new GlobalException(0,"当前是第一条,不能继续往上移动");
        }

        //交换数据
        SysUser sysUserUpdateSort = new SysUser();
        BeanUtils.copyProperties(sysUserUpdateSortQuery,sysUserUpdateSort);
        sysUserUpdateSort.setShowOrder(sysUser.getShowOrder());
        sysUserUpdateSort.setLastUpdateTime(new Date());
        sysUserMapper.updateById(sysUserUpdateSort);


        //修改
        sysUser.setShowOrder(showOrder);
        sysUser.setLastUpdateTime(new Date());
        sysUserMapper.updateById(sysUser);

    }

    //下移人员
    @Transactional(value = "adminTransactionManager")
    @Override
    public void moveDownSysUserSort(SysUserUpdateSortQuery sysUserUpdateSortQuery){

        int showOrder = sysUserUpdateSortQuery.getShowOrder();

        //查询下一条记录，小于当前的第一条数据
        QueryWrapper<SysUser> orgIdQueryWrapper = new QueryWrapper();
        orgIdQueryWrapper.eq("orgId",sysUserUpdateSortQuery.getOrgId());
        orgIdQueryWrapper.lt("showOrder",showOrder); //小于
        orgIdQueryWrapper.eq("status",1);
        orgIdQueryWrapper.orderByDesc("showOrder").last("limit 1");
        SysUser sysUser = sysUserMapper.selectOne(orgIdQueryWrapper);
        if(sysUser == null){
            throw new GlobalException(0,"当前是最后一条,不能继续往下移动");
        }


        //交换数据
        SysUser sysUserUpdateSort = new SysUser();
        BeanUtils.copyProperties(sysUserUpdateSortQuery,sysUserUpdateSort);
        sysUserUpdateSort.setShowOrder(sysUser.getShowOrder());
        sysUserUpdateSort.setLastUpdateTime(new Date());
        sysUserMapper.updateById(sysUserUpdateSort);


        //修改
        sysUser.setShowOrder(showOrder);
        sysUser.setLastUpdateTime(new Date());
        sysUserMapper.updateById(sysUser);

    }


    //根据账号和密码获取用户信息
    public SysUser getSysUserByAccount(String account){
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        queryWrapper.eq("status",1);
        queryWrapper.orderByDesc("showOrder");
        return sysUserMapper.selectOne(queryWrapper);

    }

}
