package com.hongkun.service.admin;

import com.hongkun.model.admin.SysOrgVO;
import com.hongkun.query.admin.SysOrgSaveQuery;
import com.hongkun.query.admin.SysOrgUpdateQuery;
import com.hongkun.query.admin.SysOrgUpdateSortQuery;

import java.util.List;

/**
 * @ClassName SysOrgService
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/16 11:49
 */
public interface SysOrgService {

    //获取组织数结构
    List<SysOrgVO> getSysOrgTreeList();

    //保存组织
    int saveSysOrg(SysOrgSaveQuery sysOrgSaveQuery);

    //修改组织
    int updateSysOrg(SysOrgUpdateQuery sysOrgUpdateQuery);

    //上移组织
    void moveUpSysOrgSort(SysOrgUpdateSortQuery sysOrgUpdateSortQuery);

    //下移组织
    void moveDownSysOrgSort(SysOrgUpdateSortQuery sysOrgUpdateSortQuery);

}
