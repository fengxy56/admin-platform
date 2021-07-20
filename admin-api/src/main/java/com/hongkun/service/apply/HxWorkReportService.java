package com.hongkun.service.apply;

import com.hongkun.query.hx.HxWorkReportQuery;
import com.hongkun.model.vo.hx.HxWorkReportDailySummaryVO;
import com.hongkun.model.vo.hx.UserWorkReportCountVO;

import java.util.List;

/**
 * @ClassName HxWorkReportService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/15 10:49
 */
public interface HxWorkReportService {

    List<HxWorkReportDailySummaryVO> getHxWorkReportDailySummaryCountList(HxWorkReportQuery hxWorkReportQuery);

    List<UserWorkReportCountVO> getUserWorkReportCountList(HxWorkReportQuery hxWorkReportQuery);

}
