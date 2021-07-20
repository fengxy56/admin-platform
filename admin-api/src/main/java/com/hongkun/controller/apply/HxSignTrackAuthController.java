package com.hongkun.controller.apply;

import com.alibaba.excel.EasyExcel;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hongkun.commons.ResultEnum;
import com.hongkun.config.ConfigInfo;
import com.hongkun.model.excel.hx.HxAppAuthExcel;
import com.hongkun.query.apply.HxSignTrackAuthDeleteQuery;
import com.hongkun.query.apply.HxSignTrackAuthQuery;
import com.hongkun.query.apply.HxSignTrackBathAuthQuery;
import com.hongkun.service.apply.HxSignTrackAuthService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.apply.HxSignTrackAuthVO;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.page.PageInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.util.List;

/**
 * @ClassName HxSignTrackAuthController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/17 9:30
 */


@RestController
@RequestMapping("/hxSignTrackAuth")
@Api(tags = "轻应用授权")
@Slf4j
public class HxSignTrackAuthController {

    @Autowired
    private HxSignTrackAuthService hxSignTrackAuthService;

    @Autowired
    private ConfigInfo configInfo;

    /**
     * @return
     */
    @PostMapping(value = "/saveBathHxSignTrackBathAuth")
    @ApiOperation(value = "保存用户角色轨迹列表信息", notes = "")
    public ResultVO<Integer> saveBathHxSignTrackBathAuth(@RequestBody HxSignTrackBathAuthQuery hxSignTrackBathAuthQuery) {

        hxSignTrackAuthService.saveBathHxSignTrackBathAuth(hxSignTrackBathAuthQuery);

        return ResponseUtil.success();
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
//            checkImportData(userExcelList);


        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseUtil.success(userExcelList);
    }

    /**
     * @return
     */
    @PostMapping(value = "/getHxSignTrackAuthList")
    @ApiOperation(value = "获取appid的用户角色授权列表", notes = "")
    public ResultVO<HxSignTrackAuthVO> getHxSignTrackAuthList(@RequestBody HxSignTrackAuthQuery hxSignTrackAuthQuery) {


        if (StringUtils.isNotBlank(configInfo.getHxOrgInfoId())) {
            hxSignTrackAuthQuery.setOrgInfoId(configInfo.getHxOrgInfoId());
        }


        //分页查询
        PageHelper.startPage(hxSignTrackAuthQuery.getPageNo(), hxSignTrackAuthQuery.getPageSize());

        List<HxSignTrackAuthVO> list = hxSignTrackAuthService.getHxSignTrackAuthList(hxSignTrackAuthQuery);

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
    @PostMapping(value = "/deleteHxSignTrackAuthList")
    @ApiOperation(value = "批量取消授权功能", notes = "")
    public ResultVO<Integer> deleteAppUserRoleAuthList(@RequestBody HxSignTrackAuthDeleteQuery hxSignTrackAuthDeleteQuery) {
        Integer deleteCount = hxSignTrackAuthService.deleteHxSignTrackAuthList(hxSignTrackAuthDeleteQuery);
        return ResponseUtil.success(deleteCount);
    }


}
