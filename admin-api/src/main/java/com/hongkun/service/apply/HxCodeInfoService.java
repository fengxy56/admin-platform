package com.hongkun.service.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.HxCodeInfo;
import com.hongkun.query.apply.HxCodeInfoQuery;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName HxCodeInfoService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/27 14:30
 */
public interface HxCodeInfoService {

    //分页查询二维码信息
    IPage<HxCodeInfo> getHxCodeInfoList(HttpServletRequest request, HxCodeInfoQuery hxCodeInfoQuery);

    //保存或者修改二维码信息
    void saveOrUpdateHxCodeInfo(HxCodeInfoQuery hxCodeInfoQuery);

}
