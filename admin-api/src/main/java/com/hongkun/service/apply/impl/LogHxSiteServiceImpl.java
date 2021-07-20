package com.hongkun.service.apply.impl;

import com.hongkun.mapper.apply.LogHxSiteMapper;
import com.hongkun.query.hx.LogHxSiteQuery;
import com.hongkun.service.apply.LogHxSiteService;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import com.hongkun.model.vo.hx.LogSiteUserIdVisitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName LogHxSiteServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/5 19:16
 */
@Service
public class LogHxSiteServiceImpl implements LogHxSiteService {


    @Autowired
    private LogHxSiteMapper logHxSiteMapper;


    public List<LogHxSiteVO> getLogHxSiteList(LogHxSiteQuery logHxSiteQuery) {


//        if(StringUtils.isNotBlank(logHxSiteQuery.getStartTime())){
//            logHxSiteQuery.setStartTime(logHxSiteQuery.getStartTime()+ " 00:00:00");
//        }
//
//        if(StringUtils.isNotBlank(logHxSiteQuery.getEndTime())){
//            logHxSiteQuery.setEndTime(logHxSiteQuery.getEndTime()+ " 23:59:59");
//        }
        List<LogHxSiteVO> logHxSiteVOList = logHxSiteMapper.getLogHxSiteList(logHxSiteQuery);
        return logHxSiteVOList;

    }


    //根据查找开始时间和结束时间 --每日访问次数
    public List<LogSiteUserIdVisitVO> getUserVisitSiteDetailCountList(String startTime, String endTime) {

//        if(StringUtils.isNotBlank(startTime)){
//            startTime = startTime + " 00:00:00";
//        }
//
//        if(StringUtils.isNotBlank(endTime)){
//            endTime = endTime + " 23:59:59";
//        }

        return logHxSiteMapper.getUserVisitSiteDetailCountList(startTime, endTime);
    }

    //根据查找开始时间和结束时间-- 账户汇总次数
    public List<LogSiteUserIdVisitVO> getUserVisitSiteTotalCountList(String startTime, String endTime) {
//        if(StringUtils.isNotBlank(startTime)){
//            startTime = startTime + " 00:00:00";
//        }
//
//        if(StringUtils.isNotBlank(endTime)){
//            endTime = endTime + " 23:59:59";
//        }
        return logHxSiteMapper.getUserVisitSiteTotalCountList(startTime, endTime);
    }

    //根据查找开始时间和结束时间--- 组织汇总次数
    public List<LogSiteUserIdVisitVO> getDeptVisitSiteTotalCountList(String startTime, String endTime) {

//        if(StringUtils.isNotBlank(startTime)){
//            startTime = startTime + " 00:00:00";
//        }
//
//        if(StringUtils.isNotBlank(endTime)){
//            endTime = endTime + " 23:59:59";
//        }
        return logHxSiteMapper.getDeptVisitSiteTotalCountList(startTime, endTime);
    }

}
