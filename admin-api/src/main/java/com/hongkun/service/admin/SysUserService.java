package com.hongkun.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.SysUser;
import com.hongkun.query.admin.SysUserQuery;
import com.hongkun.query.admin.SysUserSaveQuery;
import com.hongkun.query.admin.SysUserUpdateSortQuery;

/**
 * @ClassName SysUserService
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/16 14:14
 */
public interface SysUserService {

    //根据账号和密码获取用户信息
    SysUser getSysUserByAccount(String account);

    //分页查询用户信息
    IPage<SysUser> getSysUserList(SysUserQuery sysUserQuery);

    //保存用户
    int saveSysUser(SysUserSaveQuery sysUserSaveQuery);


    //修改用户
    int updateSysUser(SysUserSaveQuery sysUserSaveQuery);

    //上移数据
    void moveUpSysUserSort(SysUserUpdateSortQuery sysUserUpdateSortQuery);

    //下移数据
    void moveDownSysUserSort(SysUserUpdateSortQuery sysUserUpdateSortQuery);


}
