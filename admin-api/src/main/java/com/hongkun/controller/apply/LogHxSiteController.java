package com.hongkun.controller.apply;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongkun.bean.apply.LogHxSite;
import com.hongkun.model.excel.site.LogSiteDeptTotalExcel;
import com.hongkun.model.excel.site.LogSiteUserDetailExcel;
import com.hongkun.model.excel.site.LogSiteUserTotalExcel;
import com.hongkun.model.excel.site.LogSiteUserlExcel;
import com.hongkun.query.hx.LogHxSiteQuery;
import com.hongkun.service.apply.LogHxSiteService;
import com.hongkun.until.DateUtil;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import com.hongkun.model.vo.hx.LogSiteUserIdVisitVO;
import com.hongkun.model.vo.page.LogHxSitePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName LogHxSiteController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/5 19:41
 */
@RestController
@RequestMapping("/logHxSite")
@Api(tags = "智慧工地日志")
@Slf4j
public class LogHxSiteController {

    @Autowired
    private LogHxSiteService logHxSiteService;

    /**
     * @return
     */
    @ApiOperation(value = "获取智慧工地点击日志", notes = "")
    @PostMapping(value = "/getLogHxSiteList")
    public ResultVO<LogHxSite> getLogHxSiteList(@RequestBody LogHxSiteQuery logHxSiteQuery) {


        //分页查询
        PageHelper.startPage(logHxSiteQuery.getPageNo(), logHxSiteQuery.getPageSize());

        List<LogHxSiteVO> logHxSiteVOList = logHxSiteService.getLogHxSiteList(logHxSiteQuery);
        //获取分页后的数据和信息
        PageInfo pageInfo = new PageInfo(logHxSiteVOList);

        LogHxSitePageVO logHxSitePageVO = new LogHxSitePageVO();
        logHxSitePageVO.setRecords(pageInfo.getList());
        logHxSitePageVO.setCurrent(pageInfo.getPageNum());//当前页面
        logHxSitePageVO.setSize(pageInfo.getSize());//当前页的数量
        logHxSitePageVO.setPages(pageInfo.getPages());//总页数
        logHxSitePageVO.setTotal(pageInfo.getTotal());//总数量

        return ResponseUtil.success(logHxSitePageVO);

    }


    /**
     * excel文件的下载
     */
    @GetMapping("/exportExcel")
    @ApiOperation(value = "智慧工地访问合计汇总", notes = "")
    public void exportExcel(HttpServletResponse response, String startTime, String endTime) {
        try {
            response.setCharacterEncoding("utf-8");

            response.setContentType("application/vnd.ms-excel");
            String fileName = "";
            if (StringUtils.isBlank(startTime) || "undefined".equals(startTime)) {
                startTime = "2019-08-01 00:00:00";
            }
            if (StringUtils.isBlank(endTime) || "undefined".equals(endTime)) {
                endTime = DateUtil.getCurrent();
            }


            fileName = startTime.replaceAll("-", ".") + "_" + endTime.replaceAll("-", ".") + "_智慧工地汇总";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

            //这里 需要指定写用哪个class去写

            WriteSheet writeSheet = EasyExcel.writerSheet(0, "访问明细").head(LogSiteUserlExcel.class).build();
            //List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList = logHxSiteService.getUserVisitSiteDetailCountList(startTime,endTime);

            LogHxSiteQuery logHxSiteQuery = new LogHxSiteQuery();
            logHxSiteQuery.setStartTime(startTime);
            logHxSiteQuery.setEndTime(endTime);
            List<LogHxSiteVO> logHxSiteVOList = logHxSiteService.getLogHxSiteList(logHxSiteQuery);

            excelWriter.write(logHxSiteVOList, writeSheet);


            writeSheet = EasyExcel.writerSheet(1, "每日访问次数").head(LogSiteUserDetailExcel.class).build();
            List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList1 = logHxSiteService.getUserVisitSiteDetailCountList(startTime, endTime);
            excelWriter.write(logSiteUserIdVisitVOList1, writeSheet);


            writeSheet = EasyExcel.writerSheet(2, "账户汇总次数").head(LogSiteUserTotalExcel.class).build();
            List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList2 = logHxSiteService.getUserVisitSiteTotalCountList(startTime, endTime);
            excelWriter.write(logSiteUserIdVisitVOList2, writeSheet);


            writeSheet = EasyExcel.writerSheet(3, "组织汇总次数").head(LogSiteDeptTotalExcel.class).build();
            List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList3 = logHxSiteService.getDeptVisitSiteTotalCountList(startTime, endTime);
            excelWriter.write(logSiteUserIdVisitVOList3, writeSheet);

            //千万别忘记finish 会帮忙关闭流
            excelWriter.finish();


        } catch (IOException e) {
            e.printStackTrace();
            log.info("-----------------------------------");
            log.info(e.getMessage());
        }
    }


}
