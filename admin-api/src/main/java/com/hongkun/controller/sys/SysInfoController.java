package com.hongkun.controller.sys;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SysInfo;
import com.hongkun.config.ConfigInfo;
import com.hongkun.query.SysInfoQuery;
import com.hongkun.service.apply.SysInfoService;
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
@RequestMapping("/sysInfo")
@Api(tags = "接口系统信息")
public class SysInfoController {

    /**
     * LOG
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConfigInfo configInfo;

    @Autowired
    private SysInfoService hkSysInfoService;

    /**
     * @return
     */
    @ApiOperation(value = "获取接口所有系统信息", notes = "获取系统信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页数", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页显示条数", example = "5"),
            @ApiImplicitParam(paramType = "query", name = "sysName", value = "系统名称", example = "5"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "0禁用，1启用", example = "0"),

    })
    @GetMapping(value = "/getSysInfoList")
    public ResultVO<SysInfo> getHkInterfaceSysInfoList(Integer pageNo, Integer pageSize, Integer status, String sysName) {
        logger.info("获取接口所有系统信息");
        //去数据库中查询数据
        IPage<SysInfo> sysInfoList = hkSysInfoService.getSysInfoList(pageNo, pageSize, status, sysName);
        return ResponseUtil.success(sysInfoList);
    }


    /**
     * @return
     */
    @ApiOperation(value = "保存或者修改系统信息", notes = "")
    @PostMapping(value = "/saveOrUpdateSysInfo")
    public ResultVO saveHkInterfaceSysInfo(@RequestBody SysInfoQuery sysInfoQuery) {

        logger.info("保存或者修改系统信息:" + sysInfoQuery.toString());
        hkSysInfoService.saveOrUpdateSysInfo(sysInfoQuery);
        return ResponseUtil.success();
    }


}