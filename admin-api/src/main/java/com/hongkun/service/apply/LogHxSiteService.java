package com.hongkun.service.apply;

import com.hongkun.query.hx.LogHxSiteQuery;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import com.hongkun.model.vo.hx.LogSiteUserIdVisitVO;

import java.util.List;

/**
 * @ClassName LogHxSiteService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/5 19:16
 */
public interface LogHxSiteService {

    //分页查询日志
    List<LogHxSiteVO> getLogHxSiteList(LogHxSiteQuery logHxSiteQuery);


    //根据查找开始时间和结束时间 --每日访问次数
    List<LogSiteUserIdVisitVO> getUserVisitSiteDetailCountList(String startTime, String endTime);

    //根据查找开始时间和结束时间-- 账户汇总次数
    List<LogSiteUserIdVisitVO> getUserVisitSiteTotalCountList(String startTime, String endTime);

    //根据查找开始时间和结束时间--- 组织汇总次数
    List<LogSiteUserIdVisitVO> getDeptVisitSiteTotalCountList(String startTime, String endTime);


}
