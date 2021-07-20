package com.hongkun.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.SysOrg;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.admin.SysOrgMapper;
import com.hongkun.model.admin.SysOrgVO;
import com.hongkun.query.admin.SysOrgSaveQuery;
import com.hongkun.query.admin.SysOrgUpdateQuery;
import com.hongkun.query.admin.SysOrgUpdateSortQuery;
import com.hongkun.service.admin.SysOrgService;
import com.hongkun.until.CodeGeneratedUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName SysOrgServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/16 11:50
 */
@Service
public class SysOrgServiceImpl implements SysOrgService {

    @Resource
    private SysOrgMapper sysOrgMapper;

    //获取组织数结构
    @Override
    public List<SysOrgVO> getSysOrgTreeList() {

        QueryWrapper<SysOrg> sysOrgQueryWrapper = new QueryWrapper<>();
        sysOrgQueryWrapper.orderByDesc("showOrder");
        List<SysOrg> rootMenu = sysOrgMapper.selectList(sysOrgQueryWrapper);
        // 最后的结果
        List<SysOrgVO> hxOrgVOList = new ArrayList<>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId为-1
            if (StringUtils.isNotBlank(rootMenu.get(i).getParentId()) && "-1".equals(rootMenu.get(i).getParentId())) {
                SysOrgVO menuVO = new SysOrgVO();
                BeanUtils.copyProperties(rootMenu.get(i), menuVO);

                menuVO.setMenuId(menuVO.getId());
                menuVO.setMenuName(menuVO.getOrgName());
                menuVO.setTitle(menuVO.getOrgName());
                hxOrgVOList.add(menuVO);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysOrgVO hxOrgVO : hxOrgVOList) {
            hxOrgVO.setChildren(this.getChild(hxOrgVO.getMenuId(), rootMenu));
        }

        return hxOrgVOList;
    }

    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysOrgVO> getChild(String id, List<SysOrg> rootMenu) {
        // 子菜单
        List<SysOrgVO> childList = new ArrayList<>();

        for (SysOrg menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId()) && menu.getParentId().equals(id)) {
                SysOrgVO menuVO = new SysOrgVO();
                BeanUtils.copyProperties(menu, menuVO);

                menuVO.setMenuId(menuVO.getId());
                menuVO.setMenuName(menuVO.getOrgName());
                menuVO.setTitle(menuVO.getOrgName());

                childList.add(menuVO);
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysOrgVO menuVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            menuVO.setChildren(this.getChild(menuVO.getMenuId(), rootMenu));
        }
        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }



    @Transactional(value = "adminTransactionManager")
    @Override
    public int saveSysOrg(SysOrgSaveQuery sysOrgSaveQuery) {
        int count = 0;


        SysOrg sysOrg = new  SysOrg();
        BeanUtils.copyProperties(sysOrgSaveQuery,sysOrg);

        sysOrg.setId(CodeGeneratedUtil.genUUID());
        sysOrg.setCreateTime(new Date());
        sysOrg.setLastUpdateTime(new Date());

        //设置排序字段
        QueryWrapper<SysOrg> showOrderQueryWrapper = new QueryWrapper<>();
        showOrderQueryWrapper.eq("parentId",sysOrgSaveQuery.getParentId());
        showOrderQueryWrapper.orderByDesc("showOrder").last("limit 1");
        SysOrg showOrderSysOrg = sysOrgMapper.selectOne(showOrderQueryWrapper);
        if(showOrderSysOrg !=null){
            sysOrg.setShowOrder(showOrderSysOrg.getShowOrder()+1);
        }else{
            sysOrg.setShowOrder(1);
        }

        //设置组织编号
        QueryWrapper<SysOrg> orgNumberQueryWrapper = new QueryWrapper<>();
        orgNumberQueryWrapper.eq("parentId",sysOrgSaveQuery.getParentId());
        orgNumberQueryWrapper.orderByDesc("orgNumber").last("limit 1");
        SysOrg orgNumberSysOrg = sysOrgMapper.selectOne(orgNumberQueryWrapper);
        if(orgNumberSysOrg !=null){
            sysOrg.setOrgNumber(orgNumberSysOrg.getOrgNumber()+1);
        }else{
            sysOrg.setOrgNumber(100);
        }


        //设置长组织名称或者组织编号
        SysOrg sysOrgParent = sysOrgMapper.selectById(sysOrgSaveQuery.getParentId());
        if(sysOrgParent !=null ){
            sysOrg.setOrgLongName(sysOrgParent.getOrgName()+"_"+sysOrg.getOrgName());
            sysOrg.setOrgLongNumber(sysOrgParent.getOrgNumber()+"_"+sysOrg.getOrgNumber());
        }


        count = sysOrgMapper.insert(sysOrg);
        return count;
    }



    @Transactional(value = "adminTransactionManager")
    @Override
    public int updateSysOrg(SysOrgUpdateQuery sysOrgUpdateQuery) {
        SysOrg sysOrg = new  SysOrg();
        BeanUtils.copyProperties(sysOrgUpdateQuery,sysOrg);
        sysOrg.setLastUpdateTime(new Date());
        return sysOrgMapper.updateById(sysOrg);
    }





    //上移组织
    @Transactional(value = "adminTransactionManager")
    @Override
    public void moveUpSysOrgSort(SysOrgUpdateSortQuery sysOrgUpdateSortQuery){

        int showOrder = sysOrgUpdateSortQuery.getShowOrder();

        //查询上一条记录，
        QueryWrapper<SysOrg> parentIdQueryWrapper = new QueryWrapper();
        parentIdQueryWrapper.eq("parentId",sysOrgUpdateSortQuery.getParentId());
        parentIdQueryWrapper.gt("showOrder",showOrder);//大于
        parentIdQueryWrapper.eq("status",1);
        parentIdQueryWrapper.orderByAsc("showOrder").last("limit 1");
        SysOrg sysOrg = sysOrgMapper.selectOne(parentIdQueryWrapper);
        if(sysOrg == null){
            throw new GlobalException(0,"当前是第一条,不能继续往上移动");
        }

        //交换数据
        SysOrg sysOrgUpdateSort = new SysOrg();
        BeanUtils.copyProperties(sysOrgUpdateSortQuery,sysOrgUpdateSort);
        sysOrgUpdateSort.setShowOrder(sysOrg.getShowOrder());
        sysOrgUpdateSort.setLastUpdateTime(new Date());
        sysOrgMapper.updateById(sysOrgUpdateSort);


        //修改
        sysOrg.setShowOrder(showOrder);
        sysOrg.setLastUpdateTime(new Date());
        sysOrgMapper.updateById(sysOrg);

    }

    //下移组织
    @Transactional(value = "adminTransactionManager")
    @Override
    public void moveDownSysOrgSort(SysOrgUpdateSortQuery sysOrgUpdateSortQuery){
        int showOrder = sysOrgUpdateSortQuery.getShowOrder();

        //查询上一条记录，
        QueryWrapper<SysOrg> parentIdQueryWrapper = new QueryWrapper();
        parentIdQueryWrapper.eq("parentId",sysOrgUpdateSortQuery.getParentId());
        parentIdQueryWrapper.lt("showOrder",showOrder);//大于
        parentIdQueryWrapper.eq("status",1);
        parentIdQueryWrapper.orderByDesc("showOrder").last("limit 1");
        SysOrg sysOrg = sysOrgMapper.selectOne(parentIdQueryWrapper);
        if(sysOrg == null){
            throw new GlobalException(0,"当前是最后一条,不能继续往下移动");
        }

        //交换数据
        SysOrg sysOrgUpdateSort = new SysOrg();
        BeanUtils.copyProperties(sysOrgUpdateSortQuery,sysOrgUpdateSort);
        sysOrgUpdateSort.setShowOrder(sysOrg.getShowOrder());
        sysOrgUpdateSort.setLastUpdateTime(new Date());
        sysOrgMapper.updateById(sysOrgUpdateSort);


        //修改
        sysOrg.setShowOrder(showOrder);
        sysOrg.setLastUpdateTime(new Date());
        sysOrgMapper.updateById(sysOrg);

    }

}
