package com.hongkun.service.hxgol;

import com.hongkun.query.hx.AppUserRoleAuthQuery;
import com.hongkun.query.hx.HxAppUserBathAuthQuery;
import com.hongkun.query.hx.HxUserRoleAppAuthQuery;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;

import java.util.List;

/**
 * @ClassName HxAppAuthService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/28 16:08
 */
public interface HxAppAuthService {

    //获取用户角色授权列表
    List<UserRoleAppVO> getHxUserRoleAppTree(String authType, String authId);


    //保存用户或者角色授权轻应用
    void saveHxUserRoleAppAuth(HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery);


    //批量保存用户授权轻应用
    String saveBathHxUserRoleAppAuth(HxAppUserBathAuthQuery hxAppUserBathAuthQuery);

    //获取appid的用户角色授权列表
    List<AppUserRoleAuthVO> getAppUserRoleAuthList(AppUserRoleAuthQuery appUserRoleAuthQuery);

}
