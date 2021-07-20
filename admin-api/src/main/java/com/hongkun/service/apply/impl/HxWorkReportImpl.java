package com.hongkun.service.apply.impl;

import com.hongkun.mapper.apply.HxWorkReportMapper;
import com.hongkun.query.hx.HxWorkReportQuery;
import com.hongkun.model.vo.hx.UserWorkReportCountVO;
import com.hongkun.service.apply.HxWorkReportService;
import com.hongkun.model.vo.hx.HxWorkReportDailySummaryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName HxWorkReportImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/15 10:50
 */
@Service
public class HxWorkReportImpl implements HxWorkReportService {

    @Autowired
    private HxWorkReportMapper hxWorkReportMapper;

    public List<HxWorkReportDailySummaryVO> getHxWorkReportDailySummaryCountList(HxWorkReportQuery hxWorkReportQuery) {
        return hxWorkReportMapper.getHxWorkReportDailySummaryCountList(hxWorkReportQuery);
    }

    public List<UserWorkReportCountVO> getUserWorkReportCountList(HxWorkReportQuery hxWorkReportQuery) {
        return hxWorkReportMapper.getUserWorkReportCountList(hxWorkReportQuery);
    }


}
