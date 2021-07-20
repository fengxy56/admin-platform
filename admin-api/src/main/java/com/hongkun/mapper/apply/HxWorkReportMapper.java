package com.hongkun.mapper.apply;

import com.hongkun.query.hx.HxWorkReportQuery;
import com.hongkun.model.vo.hx.HxWorkReportDailySummaryVO;
import com.hongkun.model.vo.hx.UserWorkReportCountVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName HxWorkReportMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/15 10:47
 */
@Component
public interface HxWorkReportMapper {


    List<HxWorkReportDailySummaryVO> getHxWorkReportDailySummaryCountList(HxWorkReportQuery hxWorkReportQuery);

    List<UserWorkReportCountVO> getUserWorkReportCountList(HxWorkReportQuery hxWorkReportQuery);

}
