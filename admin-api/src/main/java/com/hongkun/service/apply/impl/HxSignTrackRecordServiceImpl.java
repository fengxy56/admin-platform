package com.hongkun.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.HxSignTrackConfigure;
import com.hongkun.bean.apply.HxSignTrackRecord;
import com.hongkun.mapper.apply.HxSignTrackRecordMapper;
import com.hongkun.model.vo.apply.HxSignTrackRecordSummaryVO;
import com.hongkun.model.vo.apply.HxSignTrackRecordVO;
import com.hongkun.query.apply.HxSignTrackConfigureQuery;
import com.hongkun.query.apply.HxSignTrackRecordQuery;
import com.hongkun.query.apply.HxSignTrackRecordSummaryQuery;
import com.hongkun.service.apply.HxSignTrackRecordService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName HxSignTrackRecordServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/2 9:21
 */
@Service
public class HxSignTrackRecordServiceImpl implements HxSignTrackRecordService {

    @Resource
    private HxSignTrackRecordMapper hxSignTrackRecordMapper;

    //获取签到记录
    public List<HxSignTrackRecordVO> getUserSignTrackRecordList(HxSignTrackRecordQuery hxSignTrackRecordQuery) {
        List<HxSignTrackRecordVO> list = hxSignTrackRecordMapper.getUserSignTrackRecordList(hxSignTrackRecordQuery);
        return list;
    }



    //获取签到记录汇总
    public List<HxSignTrackRecordSummaryVO> getHxSignTrackRecordSummary(HxSignTrackRecordSummaryQuery hxSignTrackRecordSummaryQuery) {
        return hxSignTrackRecordMapper.getHxSignTrackRecordSummary(hxSignTrackRecordSummaryQuery);
    }



}
