package com.hongkun.service.hxgol;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.hx.HxCustAppInfo;
import com.hongkun.model.vo.hx.HxCustAppInfoTreeVO;
import com.hongkun.query.hx.HxCustAppInfoQuery;
import com.hongkun.query.hx.HxCustAppInfoSaveOrUpdateQuery;

import java.util.List;

/**
 * @ClassName HxCustAppInfoService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:52
 */
public interface HxCustAppInfoService {


    //分页查询轻应用信息
    IPage<HxCustAppInfo> getHxCustAppInfoList(HxCustAppInfoQuery hxCustAppInfoQuery);

    //保存轻应用appid
    void saveHxCustAppInfo(HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery);

    //保存或者修改轻应用appid
    void updateHxCustAppInfo(HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery);


}
