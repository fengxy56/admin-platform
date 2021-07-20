package com.hongkun.service.apply;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SysInfo;
import com.hongkun.query.SysInfoQuery;

/**
 * @ClassName HkSmsSystemService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/2/26 15:48
 * @Version 1.0
 */
public interface SysInfoService {

    IPage<SysInfo> getSysInfoList(Integer page, Integer pageSize, Integer status, String sysName);

    void saveOrUpdateSysInfo(SysInfoQuery sysInfoQuery);

}
