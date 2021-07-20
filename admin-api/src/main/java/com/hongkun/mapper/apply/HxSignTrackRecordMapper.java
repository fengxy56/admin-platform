package com.hongkun.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.HxSignTrackRecord;
import com.hongkun.model.vo.apply.HxSignTrackRecordSummaryVO;
import com.hongkun.model.vo.apply.HxSignTrackRecordVO;
import com.hongkun.query.apply.HxSignTrackRecordQuery;
import com.hongkun.query.apply.HxSignTrackRecordSummaryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @ClassName HxSignTrackRecordMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/31 13:38
 */
public interface HxSignTrackRecordMapper extends BaseMapper<HxSignTrackRecord> {


    List<HxSignTrackRecordVO> getUserSignTrackRecordList(HxSignTrackRecordQuery hxSignTrackRecordQuery);

    List<HxSignTrackRecordSummaryVO> getHxSignTrackRecordSummary(HxSignTrackRecordSummaryQuery hxSignTrackRecordSummaryQuery);

}
