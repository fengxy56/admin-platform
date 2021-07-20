package com.hongkun.service.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.HxSignTrackRecord;
import com.hongkun.model.vo.apply.HxSignTrackRecordSummaryVO;
import com.hongkun.model.vo.apply.HxSignTrackRecordVO;
import com.hongkun.query.apply.HxSignTrackRecordQuery;
import com.hongkun.query.apply.HxSignTrackRecordSummaryQuery;

import java.util.List;

/**
 * @ClassName HxSignTrackRecordService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/2 9:21
 */
public interface HxSignTrackRecordService {

    //获取签到记录
    List<HxSignTrackRecordVO> getUserSignTrackRecordList(HxSignTrackRecordQuery hxSignTrackRecordQuery);


    //获取签到记录汇总
    List<HxSignTrackRecordSummaryVO> getHxSignTrackRecordSummary(HxSignTrackRecordSummaryQuery hxSignTrackRecordSummaryQuery);

}
