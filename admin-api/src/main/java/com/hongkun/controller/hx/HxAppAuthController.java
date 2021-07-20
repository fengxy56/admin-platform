package com.hongkun.controller.hx;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongkun.commons.ResultEnum;
import com.hongkun.config.ConfigInfo;
import com.hongkun.exception.GlobalException;
import com.hongkun.model.excel.hx.AppUserRoleAuthlExcel;
import com.hongkun.model.excel.hx.HxAppAuthExcel;
import com.hongkun.model.vo.hx.UserRoleAppMenuVO;
import com.hongkun.query.hx.AppUserRoleAuthQuery;
import com.hongkun.query.hx.HxAppUserBathAuthQuery;
import com.hongkun.query.hx.HxUserRoleAppAuthQuery;
import com.hongkun.service.hxgol.HxAppAuthService;
import com.hongkun.service.hxgol.HxAppMenuAuthService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;
import com.hongkun.model.vo.page.PageInfoVO;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

/**
 * @ClassName HxAppAuthController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/29 7:38
 */
@RestController
@RequestMapping("/hxAppAuth")
@Api(tags = "轻应用授权")
@Slf4j
public class HxAppAuthController {

    @Autowired
    private HxAppAuthService hxAppAuthService;

    @Autowired
    private ConfigInfo configInfo;

    @Autowired
    private HxAppMenuAuthService hxAppMenuAuthService;


    /**
     * @return
     */
    @GetMapping(value = "/getHxUserRoleAppTree")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "authId", value = "用户账户/角色id", example = "fengxiaoyang"),
            @ApiImplicitParam(paramType = "query", name = "authType", value = "权限类型，1用户，2角色", example = "fengxiaoyang"),
    })
    @ApiOperation(value = "获取用户角色轻应用列表信息", notes = "")
    public ResultVO<UserRoleAppVO> getHxUserRoleAppTree(String authType, String authId) {

        List<UserRoleAppVO> list = hxAppAuthService.getHxUserRoleAppTree(authType, authId);
        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @PostMapping(value = "/saveHxUserRoleAppAuth")
    @ApiOperation(value = "保存用户角色轻应用列表信息", notes = "")
    public ResultVO<Integer> saveHxUserRoleAppAuth(@RequestBody HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery) {

        hxAppAuthService.saveHxUserRoleAppAuth(hxUserRoleAppAuthQuery);

        return ResponseUtil.success();
    }

    /**
     * @return
     */
    @PostMapping(value = "/saveBathHxUserRoleAppAuth")
    @ApiOperation(value = "批量保存用户角色轻应用列表信息", notes = "")
    public ResultVO<String> saveBathHxUserRoleAppAuth(@RequestBody HxAppUserBathAuthQuery hxAppUserBathAuthQuery) {

        String message = hxAppAuthService.saveBathHxUserRoleAppAuth(hxAppUserBathAuthQuery);
        log.info("批量保存用户角色轻应用列表信息:" + message);

        return ResponseUtil.success(message);
    }

    /**
     * @return
     */
    @PostMapping(value = "/getAppUserRoleAuthList")
    @ApiOperation(value = "获取appid的用户角色授权列表", notes = "")
    public ResultVO<AppUserRoleAuthVO> getAppUserRoleAuthList(@RequestBody AppUserRoleAuthQuery appUserRoleAuthQuery) {


        if (StringUtils.isNotBlank(configInfo.getHxOrgInfoId())) {
            appUserRoleAuthQuery.setOrgInfoId(configInfo.getHxOrgInfoId());
        }


        //分页查询
        PageHelper.startPage(appUserRoleAuthQuery.getPageNo(), appUserRoleAuthQuery.getPageSize());

        List<AppUserRoleAuthVO> list = hxAppAuthService.getAppUserRoleAuthList(appUserRoleAuthQuery);

        //获取分页后的数据和信息
        PageInfo pageInfo = new PageInfo(list);

        PageInfoVO<AppUserRoleAuthVO> pageInfoVO = new PageInfoVO();
        pageInfoVO.setRecords(pageInfo.getList());
        pageInfoVO.setCurrent(pageInfo.getPageNum());//当前页面
        pageInfoVO.setSize(pageInfo.getSize());//当前页的数量
        pageInfoVO.setPages(pageInfo.getPages());//总页数
        pageInfoVO.setTotal(pageInfo.getTotal());//总数量

        return ResponseUtil.success(pageInfoVO);
    }

    /**
     * @return
     */
    @PostMapping(value = "/readExcel")
    @ApiOperation(value = "保存用户角色轻应用列表信息", notes = "")
    public ResultVO<HxAppAuthExcel> excel(HttpServletRequest request, @RequestParam("file") MultipartFile file) {

        List<HxAppAuthExcel> userExcelList = null;
        try {

            // 1.excel同步读取数据
            userExcelList = EasyExcel.read(new BufferedInputStream(file.getInputStream())).head(HxAppAuthExcel.class).sheet().doReadSync();
            log.info("list:" + userExcelList);

            // 2.检查是否大于1000条
            if (userExcelList.size() > 1000) {
//                throw new GlobalException(ResultEnum.IMPORT_EXCEL);
                return ResponseUtil.error(0, ResultEnum.IMPORT_EXCEL.getErrMsg());
            }
            // 3.导入校验所有行列格式
            checkImportData(userExcelList);
//            // 4.将 userExcelList 转成 userList
//            List<HxAppAuthExcelVM> userList = userExcelList2UserList(userExcelList);
//            // 5.入库操作
//            userService.batchInsertOrUpdate(userList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseUtil.success(userExcelList);
    }

    private void checkImportData(List<HxAppAuthExcel> userExcelList) {
        // 行号从第2行开始
        int rowNo = 2;
        // 遍历校验所有行和列
        for (HxAppAuthExcel hxAppAuthExcel : userExcelList) {
            // 1.校验用户名
            String authId = hxAppAuthExcel.getAuthId();
            if (StringUtils.isBlank(authId)) {
                throw new GlobalException(ResultEnum.CHILD_MENU_DELETE_ERROR);
            }

            // 2.校验姓名
            String authName = hxAppAuthExcel.getAuthName();
            if (StringUtils.isBlank(authName)) {
                throw new GlobalException(ResultEnum.CHILD_MENU_DELETE_ERROR);
            }

            // 2.校验类型
            Integer authType = hxAppAuthExcel.getAuthType();
            if (authType == null) {
                throw new GlobalException(ResultEnum.CHILD_MENU_DELETE_ERROR);
            }

        }
    }


    /**
     * excel文件的下载
     */
    @GetMapping("/exportExcel")
    @ApiOperation(value = "轻应用权限明细导出", notes = "")
    public void exportExcel(HttpServletResponse response, Integer authType, String appId, String appName, String authId, String authName) {
        try {
            response.setCharacterEncoding("utf-8");

            response.setContentType("application/vnd.ms-excel");
            String fileName = "";


            fileName = "轻应用权限明细";
            fileName = URLEncoder.encode(fileName, "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream()).build();

            //这里 需要指定写用哪个class去写

            WriteSheet writeSheet = EasyExcel.writerSheet(0, "明细").head(AppUserRoleAuthlExcel.class).build();
            //List<LogSiteUserIdVisitVO> logSiteUserIdVisitVOList = logHxSiteService.getUserVisitSiteDetailCountList(startTime,endTime);


            AppUserRoleAuthQuery appUserRoleAuthQuery = new AppUserRoleAuthQuery();
            if (StringUtils.isNotBlank(configInfo.getHxOrgInfoId())) {
                appUserRoleAuthQuery.setOrgInfoId(configInfo.getHxOrgInfoId());
            }
            if (StringUtils.isNotBlank(appId)) {
                appUserRoleAuthQuery.setAppId(appId);
            }
            if (StringUtils.isNotBlank(appName)) {
                appUserRoleAuthQuery.setAppName(appName);
            }
            if (StringUtils.isNotBlank(authId)) {
                appUserRoleAuthQuery.setAuthId(authId);
            }
            if (StringUtils.isNotBlank(authName)) {
                appUserRoleAuthQuery.setAuthName(authName);
            }
            if (authType != null) {
                appUserRoleAuthQuery.setAuthType(authType);
            }

            List<AppUserRoleAuthVO> appUserRoleAuthVOList = hxAppAuthService.getAppUserRoleAuthList(appUserRoleAuthQuery);

            excelWriter.write(appUserRoleAuthVOList, writeSheet);

            //千万别忘记finish 会帮忙关闭流
            excelWriter.finish();


        } catch (IOException e) {
            e.printStackTrace();
            log.info("-----------------------------------");
            log.info(e.getMessage());
        }
    }



    /**
     * @return
     */
    @GetMapping(value = "/getUserRoleAppMenuVOList")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "authId", value = "用户账户/角色id", example = "fengxiaoyang"),
            @ApiImplicitParam(paramType = "query", name = "authType", value = "权限类型，1用户，2角色", example = "fengxiaoyang"),
            @ApiImplicitParam(paramType = "query", name = "parentAppId", value = "父级appId", example = "fengxiaoyang"),
    })
    @ApiOperation(value = "获取轻应用菜单列表", notes = "")
    public ResultVO<UserRoleAppMenuVO> getUserRoleAppMenuVOList(String authType, String authId,String parentAppId) {

        List<UserRoleAppMenuVO> list = hxAppMenuAuthService.getUserRoleAppMenuVOList(authType, authId, parentAppId);
        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @PostMapping(value = "/saveUserRoleHxAppMenuAuth")
    @ApiOperation(value = "保存用户角色轻应用列表信息", notes = "")
    public ResultVO<Integer> saveUserRoleHxAppMenuAuth(@RequestBody HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery) {

        hxAppMenuAuthService.saveUserRoleHxAppMenuAuth(hxUserRoleAppAuthQuery);

        return ResponseUtil.success();
    }


}
