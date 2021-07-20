package com.hongkun.service.apply;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SmsSysInfo;
import com.hongkun.query.SmsSysInfoQuery;

/**
 * @ClassName HkSmsSystemService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/2/26 15:48
 * @Version 1.0
 */
public interface SmsSysInfoService {

    //分页查询短信系统信息
    IPage<SmsSysInfo> getSmsSysInfoList(Integer pageNo, Integer pageSize, Integer status, String sysName);

    //保存或者修改新短信系统信息
    void saveOrUpdateSmsSysInfo(SmsSysInfoQuery smsSysInfoQuery);

}
