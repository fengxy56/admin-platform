package com.hongkun.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.LogHxSite;
import com.hongkun.query.hx.LogHxSiteQuery;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import com.hongkun.model.vo.hx.LogSiteUserIdVisitVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName LogHxSiteMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/5 19:15
 */
@Component
public interface LogHxSiteMapper extends BaseMapper<LogHxSite> {


    List<LogSiteUserIdVisitVO> getUserVisitSiteXxCountList(@Param("startTime") String startTime,
                                                           @Param("endTime") String endTime);


    List<LogSiteUserIdVisitVO> getUserVisitSiteHzCountList(@Param("startTime") String startTime,
                                                           @Param("endTime") String endTime);


    //分页查询智慧工地访问字数
    List<LogHxSiteVO> getLogHxSiteList(LogHxSiteQuery logHxSiteQuery);


    //用户详情次数汇总
    List<LogSiteUserIdVisitVO> getUserVisitSiteDetailCountList(@Param("startTime") String startTime,
                                                               @Param("endTime") String endTime);


    //用户时间汇总
    List<LogSiteUserIdVisitVO> getUserVisitSiteTotalCountList(@Param("startTime") String startTime,
                                                              @Param("endTime") String endTime);

    //组织汇总
    List<LogSiteUserIdVisitVO> getDeptVisitSiteTotalCountList(@Param("startTime") String startTime,
                                                              @Param("endTime") String endTime);

}
