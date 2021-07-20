package com.hongkun.controller.hx;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongkun.bean.apply.LogHxSite;
import com.hongkun.model.excel.hx.HxWorkReportDailySummaryExcel;
import com.hongkun.query.hx.HxWorkReportQuery;
import com.hongkun.model.vo.hx.UserWorkReportCountVO;
import com.hongkun.service.apply.HxWorkReportService;
import com.hongkun.until.DateUtil;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.hx.HxWorkReportDailySummaryVO;
import com.hongkun.model.vo.page.PageInfoVO;
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
 * @ClassName HxWorkReportController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/15 10:51
 */
@RestController
@RequestMapping("/hxWorkReport")
@Api(tags = "工作更新")
@Slf4j
public class HxWorkReportController {

    @Autowired
    private HxWorkReportService hxWorkReportService;


    /**
     * @return
     */
    @ApiOperation(value = "获取工作更新汇总日志", notes = "")
    @PostMapping(value = "/getHxWorkReportDailySummaryCountList")
    public ResultVO<LogHxSite> getHxWorkReportDailySummaryCountList(@RequestBody HxWorkReportQuery hxWorkReportQuery) {


        //分页查询
        PageHelper.startPage(hxWorkReportQuery.getPageNo(), hxWorkReportQuery.getPageSize());

        List<HxWorkReportDailySummaryVO> logHxSiteVOList = hxWorkReportService.getHxWorkReportDailySummaryCountList(hxWorkReportQuery);
        //获取分页后的数据和信息
        PageInfo pageInfo = new PageInfo(logHxSiteVOList);

        PageInfoVO<HxWorkReportDailySummaryVO> pageInfoVO = new PageInfoVO();
        pageInfoVO.setRecords(pageInfo.getList());
        pageInfoVO.setCurrent(pageInfo.getPageNum());//当前页面
        pageInfoVO.setSize(pageInfo.getSize());//当前页的数量
        pageInfoVO.setPages(pageInfo.getPages());//总页数
        pageInfoVO.setTotal(pageInfo.getTotal());//总数量

        return ResponseUtil.success(pageInfoVO);

    }

    /**
     * excel文件的下载
     */
    @GetMapping("/exportExcel")
    @ApiOperation(value = "工作更新合计汇总", notes = "")
    public void exportExcel(HttpServletResponse response, String startTime, String endTime) {
        try {
            response.setCharacterEncoding("utf-8");

            response.setContentType("application/vnd.ms-excel");
            String fileName = "";
            if (StringUtils.isBlank(startTime) || "undefined".equals(startTime)) {
                startTime = "2019-08-01";
            }
            if (StringUtils.isBlank(endTime) || "undefined".equals(endTime)) {
                endTime = DateUtil.getCurrentDate();
            }


            fileName = startTime.replaceAll("-", ".") + "_" + endTime.replaceAll("-", ".") + "_工作更新汇总";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

            //这里 需要指定写用哪个class去写

            WriteSheet writeSheet = EasyExcel.writerSheet(0, "汇总").head(HxWorkReportDailySummaryExcel.class).build();
            //List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList = logHxSiteService.getUserVisitSiteDetailCountList(startTime,endTime);

            HxWorkReportQuery hxWorkReportQuery = new HxWorkReportQuery();
            hxWorkReportQuery.setStartTime(startTime + " 00:00:00");
            hxWorkReportQuery.setEndTime(endTime + " 23:59:59");
            List<UserWorkReportCountVO> hxWorkReportDailySummaryVOList = hxWorkReportService.getUserWorkReportCountList(hxWorkReportQuery);


            excelWriter.write(hxWorkReportDailySummaryVOList, writeSheet);

            //千万别忘记finish 会帮忙关闭流
            excelWriter.finish();


        } catch (IOException e) {
            e.printStackTrace();
            log.info("-----------------------------------");
            log.info(e.getMessage());
        }
    }


}
