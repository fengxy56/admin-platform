package com.hongkun.service.hxgol;

import com.hongkun.model.vo.hx.AppMenuUserRoleAuthVO;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppMenuVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;
import com.hongkun.query.hx.AppUserRoleAuthQuery;
import com.hongkun.query.hx.HxAppUserBathAuthQuery;
import com.hongkun.query.hx.HxUserRoleAppAuthQuery;

import java.util.List;

/**
 * @ClassName HxAppAuthService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/28 16:08
 */
public interface HxAppMenuAuthService {

    //获取用户角色授权列表
    List<UserRoleAppMenuVO> getUserRoleAppMenuVOList(String authType, String authId,String parentAppId);


    //保存用户或者角色授权轻应用
    void saveUserRoleHxAppMenuAuth(HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery);

    //获取appid的用户角色授权列表
//    List<AppMenuUserRoleAuthVO> getAppMenuUserRoleAuthList(AppUserRoleAuthQuery appUserRoleAuthQuery);

}
