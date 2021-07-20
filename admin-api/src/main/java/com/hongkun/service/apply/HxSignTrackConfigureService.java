package com.hongkun.service.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.HxSignTrackConfigure;
import com.hongkun.query.apply.HxSignTrackConfigureQuery;
import com.hongkun.query.apply.HxSignTrackConfigureSaveQuery;

/**
 * @ClassName HxSignTrackConfigureService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/19 13:55
 */
public interface HxSignTrackConfigureService {

    //获取签到地点配置文件
    IPage<HxSignTrackConfigure> getHxSignTrackConfigureList(HxSignTrackConfigureQuery hxSignTrackConfigureQuery);

    //保存签到地点配置文件
    int saveOrUpdateHxSignTrackConfigure(HxSignTrackConfigureSaveQuery hxSignTrackConfigureSaveQuery);


}
