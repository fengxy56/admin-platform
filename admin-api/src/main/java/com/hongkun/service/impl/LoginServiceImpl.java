package com.hongkun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.BusinessSystem;
import com.hongkun.bean.SysUser;
import com.hongkun.bean.UvEroleUserView;
import com.hongkun.commons.Constants;
import com.hongkun.commons.cache.TokenCache;
import com.hongkun.config.ConfigInfo;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.UvEroleUserViewMapper;
import com.hongkun.model.vo.LoginUserVO;
import com.hongkun.query.LoginUserQuery;
import com.hongkun.query.businessSystem.BusinessSystemQuery;
import com.hongkun.service.BusinessSystemService;
import com.hongkun.service.LoginService;
import com.hongkun.service.SysUserService;
import com.hongkun.until.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName LoginServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2021/4/9 10:19
 */
@Service
public class LoginServiceImpl implements LoginService {


    @Resource
    private ConfigInfo configInfo;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private BusinessSystemService businessSystemService;

    @Resource
    private UvEroleUserViewMapper uvEroleUserViewMapper;


    //获取用户登录信息
    public LoginUserVO getLoginUserByAccount(LoginUserQuery loginUserQuery){

        LoginUserVO loginUserVO = new LoginUserVO();


        SysUser sysUser = sysUserService.getSysUserByAccount(loginUserQuery.getAccount());
        if(sysUser == null){
            throw new GlobalException(0,"账号不存在！");
        }else {

            try {
                String md5Password = MD5Util.getMD5(loginUserQuery.getPassword()+"123456");
                if(md5Password.equals(sysUser.getPassword())){

                    loginUserVO.setAccountName(sysUser.getAccount());
                    loginUserVO.setChsName(sysUser.getName());

                    loginUserVO = this.getLoginUserByAccount(loginUserVO,sysUser.getAccount());

                }else{
                    throw new GlobalException(0,"密码错误！");
                }
            } catch (MD5Util.MD5Exception e) {
                e.printStackTrace();
            }

        }

        return loginUserVO;
    }



    public LoginUserVO getSsoLoginUserByAccount(String account){

        LoginUserVO loginUserVO = new LoginUserVO();

        QueryWrapper<UvEroleUserView> queryWrapper = new QueryWrapper();
        queryWrapper.eq("accountName", account);
        queryWrapper.eq("statusType", 0);
        List<UvEroleUserView> uvEroleUserViewList = uvEroleUserViewMapper.selectList(queryWrapper);
        if(uvEroleUserViewList !=null && uvEroleUserViewList.size() >0){
            loginUserVO.setChsName(uvEroleUserViewList.get(0).getChsName());
        }


        loginUserVO = this.getLoginUserByAccount(loginUserVO,account);


        return loginUserVO;
    }

    //根据账户获取用户信息
    public LoginUserVO getLoginUserByAccount(LoginUserVO loginUserVO,String account){


        loginUserVO.setAccountName(account);

        String[] adminList = configInfo.getAdministrators().split(",");

        if(Arrays.asList(adminList).contains(account)){
            List<BusinessSystem> businessSystemList = businessSystemService.getBusinessSystemNameList(new BusinessSystemQuery());
            if(businessSystemList != null && businessSystemList.size() >0){
                loginUserVO.setBusinessSystemList(businessSystemList);
                loginUserVO.setBusinessSystemAdmin(2);//2 超级管理员
            }
        }else{
            BusinessSystemQuery businessSystemQuery = new BusinessSystemQuery();
            businessSystemQuery.setAdminAccount(account);
            List<BusinessSystem> businessSystemList = businessSystemService.getBusinessSystemNameList(businessSystemQuery);
            if(businessSystemList != null && businessSystemList.size() >0){
                loginUserVO.setBusinessSystemList(businessSystemList);
                loginUserVO.setBusinessSystemAdmin(1);//1 系统管理员
            }else{
                List<BusinessSystem> list = businessSystemService.getBusinessSystemListByAccount(account);
                if(list != null && list.size() >0){
                    loginUserVO.setBusinessSystemList(list);
                }
                loginUserVO.setBusinessSystemAdmin(0);//0 普通用户
            }
        }
        return loginUserVO;
    }


}
