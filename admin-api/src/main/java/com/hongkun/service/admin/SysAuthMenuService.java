package com.hongkun.service.admin;

import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.model.vo.apply.SysAuthMenuVO;

import java.util.List;

/**
 * @ClassName SysAuthMenuService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/29 17:31
 */
public interface SysAuthMenuService {

    //根据用户和角色id获取授权列表
    List<SysAuthMenuVO> getUserLoginTreeMenu();

    //保存或者修改用户橘色授权
    void saveOrUpdateSysAuthMenu(SysAuthMenuQuery sysAuthMenuQuery);

    List<SysAuthMenuVO> getSysAuthMenuList(SysAuthMenuQuery sysAuthMenuQuery);

    //获取管理员所有列表
    List<SysAuthMenuVO> getAdminUserLoginTreeMenu();

}
