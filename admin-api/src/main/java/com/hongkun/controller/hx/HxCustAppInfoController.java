package com.hongkun.controller.hx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.hx.HxCustAppInfo;
import com.hongkun.bean.hx.HxCustAppMenu;
import com.hongkun.model.vo.hx.HxCustAppInfoTreeVO;
import com.hongkun.model.vo.hx.HxCustAppMenuTreeVO;
import com.hongkun.query.hx.HxCustAppInfoQuery;
import com.hongkun.query.hx.HxCustAppInfoSaveOrUpdateQuery;
import com.hongkun.query.hx.HxCustAppMenuSaveQuery;
import com.hongkun.service.hxgol.HxCustAppInfoService;
import com.hongkun.service.hxgol.HxCustAppMenuService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hongkun.until.ResponseUtil.error;

/**
 * @ClassName HxCustAppInfoController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:59
 */
@RestController
@RequestMapping("/hxCustAppInfo")
@Api(tags = "轻应用信息")
@Slf4j
public class HxCustAppInfoController {


    @Autowired
    private HxCustAppInfoService hxCustAppInfoService;

    @Autowired
    private HxCustAppMenuService hxCustAppMenuService;


    /**
     * @return
     */
    @ApiOperation(value = "获取轻应用信息", notes = "")
    @PostMapping(value = "/getHxCustAppInfoList")
    public ResultVO getHxCustAppInfoList(@RequestBody HxCustAppInfoQuery hxCustAppInfoQuery) {

        IPage<HxCustAppInfo> list = hxCustAppInfoService.getHxCustAppInfoList(hxCustAppInfoQuery);
        return ResponseUtil.success(list);
    }



    /**
     * @return
     */
    @ApiOperation(value = "保存轻应用信息", notes = "")
    @PostMapping(value = "/saveHxCustAppInfo")
    public ResultVO saveHxCustAppInfo(@Validated @RequestBody HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery,
                                      BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("保存轻应用信息saveHxCustAppInfo,错误参数" + errorList.get(0).getDefaultMessage());
            return error(0, errorList.get(0).getDefaultMessage());
        }

        hxCustAppInfoService.saveHxCustAppInfo(hxCustAppInfoSaveOrUpdateQuery);
        return ResponseUtil.success();

    }


    /**
     * @return
     */
    @ApiOperation(value = "修改轻应用信息", notes = "")
    @PostMapping(value = "/updateHxCustAppInfo")
    public ResultVO updateHxCustAppInfo(@Validated @RequestBody HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery,
                                        BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("修改轻应用信息updateHxCustAppInfo,错误参数" + errorList.get(0).getDefaultMessage());
            return error(0, errorList.get(0).getDefaultMessage());
        }

        hxCustAppInfoService.updateHxCustAppInfo(hxCustAppInfoSaveOrUpdateQuery);
        return ResponseUtil.success();

    }


    /**
     * @return
     */
    @ApiOperation(value = "获取轻应用菜单", notes = "")
    @PostMapping(value = "/getHxCustAppMenuList")
    public ResultVO<HxCustAppMenuTreeVO> getHxCustAppMenuList(@RequestBody HxCustAppInfoQuery hxCustAppInfoQuery) {

        IPage<HxCustAppMenuTreeVO> list = hxCustAppMenuService.getHxCustAppMenuList(hxCustAppInfoQuery);
        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "保存或者修改菜单", notes = "")
    @PostMapping(value = "/saveOrUpdateHxCustAppMenu")
    public ResultVO saveOrUpdateHxCustAppMenu(@Validated @RequestBody HxCustAppMenuSaveQuery hxCustAppMenuSaveQuery,
                                              BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("修改轻应用信息updateHxCustAppInfo,错误参数" + errorList.get(0).getDefaultMessage());
            return error(0, errorList.get(0).getDefaultMessage());
        }

        hxCustAppMenuService.saveOrUpdateHxCustAppMenu(hxCustAppMenuSaveQuery);
        return ResponseUtil.success();
    }


}
