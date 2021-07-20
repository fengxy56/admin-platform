package com.hongkun.controller.sys;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SmsSysInfo;
import com.hongkun.config.ConfigInfo;
import com.hongkun.query.SmsSysInfoQuery;
import com.hongkun.service.apply.SmsSysInfoService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/sms")
@Api(tags = "短信系统信息")
public class SmsController {

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigInfo configInfo;

    @Autowired
    private SmsSysInfoService smsSysInfoService;

    /**
     * @return
     */
    @ApiOperation(value = "获取短信系统信息", notes = "获取系统信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页数", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示条数", example = "5"),
            @ApiImplicitParam(paramType = "query", name = "sysName", value = "系统名称", example = "5"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "0禁用，1启用", example = "0"),
    })
    @GetMapping(value = "/getSmsSysInfoList")
    public ResultVO<SmsSysInfo> getSmsSysInfoList(Integer pageNo, Integer pageSize, Integer status, String sysName) {
        logger.info("获取短信系统信息");

        //去数据库中查询数据
        IPage<SmsSysInfo> smsSysInfoPage = smsSysInfoService.getSmsSysInfoList(pageNo, pageSize, status, sysName);

        return ResponseUtil.success(smsSysInfoPage);
    }


    /**
     * @return
     */
    @ApiOperation(value = "保存系统信息", notes = "保存系统信息")
    @PostMapping(value = "/saveOrUpdateSmsSysInfo")
    public ResultVO saveOrUpdateSmsSysInfo(@RequestBody SmsSysInfoQuery smsSysInfoQuery) {

        logger.info("保存短信发送系统信息参数:" + smsSysInfoQuery.toString());

        smsSysInfoService.saveOrUpdateSmsSysInfo(smsSysInfoQuery);
        return ResponseUtil.success();
    }


}