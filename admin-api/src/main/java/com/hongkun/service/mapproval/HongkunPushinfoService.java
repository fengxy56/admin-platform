package com.hongkun.service.mapproval;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.mapproval.HongkunOutsupportinfo;
import com.hongkun.bean.mapproval.HongkunPushinfo;
import com.hongkun.model.vo.hx.HongkunPushinfoVO;
import com.hongkun.query.mapproval.HongkunPushinfoQuery;
import com.hongkun.query.mapproval.HxPushInfoUpdateQuery;

import java.util.List;

/**
 * @ClassName HongkunPushinfoService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 14:15
 */
public interface HongkunPushinfoService {


    //获取移动审批信息
    IPage<HongkunPushinfoVO> getHongkunPushinfoList(HongkunPushinfoQuery hongkunPushinfoQuery);

    //修改移动审批信息
    int updateHongkunPushinfo(HxPushInfoUpdateQuery hxPushInfoUpdateQuery);


    //获取移动审批应用信息
    List<HongkunOutsupportinfo> getHongkunOutsupportinfoList(String appId);

}
