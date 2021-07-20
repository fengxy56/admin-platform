package com.hongkun.controller.apply;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongkun.bean.apply.HxSignTrackConfigure;
import com.hongkun.bean.apply.HxSignTrackRecord;
import com.hongkun.model.excel.apply.HxSignTrackRecordExcel;
import com.hongkun.model.excel.apply.HxSignTrackRecordSummaryExcel;
import com.hongkun.model.excel.site.LogSiteDeptTotalExcel;
import com.hongkun.model.excel.site.LogSiteUserDetailExcel;
import com.hongkun.model.excel.site.LogSiteUserTotalExcel;
import com.hongkun.model.excel.site.LogSiteUserlExcel;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.apply.HxSignTrackRecordSummaryVO;
import com.hongkun.model.vo.apply.HxSignTrackRecordVO;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import com.hongkun.model.vo.hx.LogSiteUserIdVisitVO;
import com.hongkun.model.vo.page.HxSignTrackRecordPageVO;
import com.hongkun.model.vo.page.LogHxSitePageVO;
import com.hongkun.query.apply.HxSignTrackConfigureQuery;
import com.hongkun.query.apply.HxSignTrackRecordQuery;
import com.hongkun.query.apply.HxSignTrackRecordSummaryQuery;
import com.hongkun.query.hx.LogHxSiteQuery;
import com.hongkun.service.apply.HxSignTrackRecordService;
import com.hongkun.until.DateUtil;
import com.hongkun.until.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName HxSignTrackRecordController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/2 9:25
 */
@RestController
@RequestMapping("/hxSignTrackRecord")
@Api(tags = "签到记录")
@Slf4j
public class HxSignTrackRecordController {

    @Resource
    private HxSignTrackRecordService hxSignTrackRecordService;

    /**
     * @return
     */
    @ApiOperation(value = "查询签到记录", notes = "")
    @PostMapping(value = "/getHxSignTrackRecordList")
    public ResultVO<HxSignTrackRecordVO> getHxSignTrackConfigureList(@RequestBody HxSignTrackRecordQuery hxSignTrackRecordQuery) {


//        long diffDay = DateUtil.timeBetween(hxSignTrackRecordQuery.getStartSignTime(),hxSignTrackRecordQuery.getEndSignTime())/(1000*600*24);
//        if(diffDay >31){
//            return ResponseUtil.error(0,"选择的时间不能超过一月");
//        }

        //分页查询
        PageHelper.startPage(hxSignTrackRecordQuery.getPageNo(), hxSignTrackRecordQuery.getPageSize());

        List<HxSignTrackRecordVO> hxSignTrackRecordVOList = hxSignTrackRecordService.getUserSignTrackRecordList(hxSignTrackRecordQuery);
        //获取分页后的数据和信息
        PageInfo pageInfo = new PageInfo(hxSignTrackRecordVOList);

        HxSignTrackRecordPageVO hxSignTrackRecordPageVO = new HxSignTrackRecordPageVO();
        hxSignTrackRecordPageVO.setRecords(pageInfo.getList());
        hxSignTrackRecordPageVO.setCurrent(pageInfo.getPageNum());//当前页面
        hxSignTrackRecordPageVO.setSize(pageInfo.getSize());//当前页的数量
        hxSignTrackRecordPageVO.setPages(pageInfo.getPages());//总页数
        hxSignTrackRecordPageVO.setTotal(pageInfo.getTotal());//总数量


        return ResponseUtil.success(hxSignTrackRecordPageVO);
    }

    /**
     * @return
     */
    @ApiOperation(value = "查询签到记录汇总", notes = "")
    @PostMapping(value = "/getHxSignTrackRecordSummary")
    public ResultVO<HxSignTrackRecordSummaryVO> getHxSignTrackRecordSummary(@RequestBody HxSignTrackRecordSummaryQuery hxSignTrackRecordSummaryQuery) {

//        long diffDay = DateUtil.timeBetween(hxSignTrackRecordSummaryQuery.getStartSignTime(),hxSignTrackRecordSummaryQuery.getEndSignTime())/(1000*600*24);
//        if(diffDay >31){
//            return ResponseUtil.error(0,"选择的时间不能超过一月");
//        }
        List<HxSignTrackRecordSummaryVO> hxSignTrackRecordSummaryVOList = hxSignTrackRecordService.getHxSignTrackRecordSummary(hxSignTrackRecordSummaryQuery);

        return ResponseUtil.success(hxSignTrackRecordSummaryVOList);
    }

    /**
     * excel文件的下载
     */
    @GetMapping("/exportExcel")
    @ApiOperation(value = "智慧工地访问合计汇总", notes = "")
    public void exportExcel(HttpServletResponse response,String startTime,String endTime,String cityCompanyName,String projectName,String jlCompanyName) {
        try {


 //           long diffDay = DateUtil.timeBetween(hxSignTrackRecordSummaryQuery.getStartSignTime(),hxSignTrackRecordSummaryQuery.getEndSignTime())/(1000*600*24);
//            if(diffDay >31){
//
//            }


            response.setCharacterEncoding("utf-8");

            response.setContentType("application/vnd.ms-excel");
            String fileName = "";
            if (StringUtils.isBlank(startTime)) {
                startTime = "2019-08-01 00:00:00";
            }

            if (StringUtils.isBlank(endTime)) {
                endTime = DateUtil.getCurrent();
            }



            fileName = startTime.replaceAll("-", ".") + "_" + endTime.replaceAll("-", ".") + "_工程监理记录汇总";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

            //这里 需要指定写用哪个class去写

            WriteSheet writeSheet = EasyExcel.writerSheet(0, "访问明细").head(HxSignTrackRecordExcel.class).build();
            //List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList = logHxSiteService.getUserVisitSiteDetailCountList(startTime,endTime);

            HxSignTrackRecordQuery hxSignTrackRecordQuery = new HxSignTrackRecordQuery();
            if(StringUtils.isNotBlank(cityCompanyName)){
                hxSignTrackRecordQuery.setCompanyName(URLDecoder.decode(cityCompanyName,"utf-8"));
            }
            if(StringUtils.isNotBlank(projectName)){
                hxSignTrackRecordQuery.setProjectName(URLDecoder.decode(projectName,"utf-8"));
            }
            hxSignTrackRecordQuery.setStartSignTime(startTime);
            hxSignTrackRecordQuery.setEndSignTime(endTime);
            List<HxSignTrackRecordVO> hxSignTrackRecordVOList = hxSignTrackRecordService.getUserSignTrackRecordList(hxSignTrackRecordQuery);

            excelWriter.write(hxSignTrackRecordVOList, writeSheet);


            writeSheet = EasyExcel.writerSheet(1, "签到记录汇总").head(HxSignTrackRecordSummaryExcel.class).build();

            HxSignTrackRecordSummaryQuery hxSignTrackRecordSummaryQuery = new HxSignTrackRecordSummaryQuery();
            if(StringUtils.isNotBlank(cityCompanyName)){
                hxSignTrackRecordSummaryQuery.setCityCompanyName(URLDecoder.decode(cityCompanyName,"utf-8"));
            }
            if(StringUtils.isNotBlank(projectName)){
                hxSignTrackRecordSummaryQuery.setProjectName(URLDecoder.decode(projectName,"utf-8"));
            }if(StringUtils.isNotBlank(jlCompanyName)){
                hxSignTrackRecordSummaryQuery.setJlCompanyName(URLDecoder.decode(jlCompanyName,"utf-8"));
            }
            hxSignTrackRecordSummaryQuery.setStartSignTime(startTime);
            hxSignTrackRecordSummaryQuery.setEndSignTime(endTime);
            List<HxSignTrackRecordSummaryVO> hxSignTrackRecordSummaryVOList = hxSignTrackRecordService.getHxSignTrackRecordSummary(hxSignTrackRecordSummaryQuery);
            excelWriter.write(hxSignTrackRecordSummaryVOList, writeSheet);



            //千万别忘记finish 会帮忙关闭流
            excelWriter.finish();


        } catch (IOException e) {
            e.printStackTrace();
            log.info("-----------------------------------");
            log.info(e.getMessage());
        }
    }


}
