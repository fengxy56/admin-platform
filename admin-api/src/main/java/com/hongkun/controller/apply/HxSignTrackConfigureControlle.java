package com.hongkun.controller.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.HxSignTrackConfigure;
import com.hongkun.query.apply.HxSignTrackConfigureQuery;
import com.hongkun.query.apply.HxSignTrackConfigureSaveQuery;
import com.hongkun.service.apply.HxSignTrackConfigureService;
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

/**
 * @ClassName HxSignTrackConfigureControlle
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/19 14:15
 */
@RestController
@RequestMapping("/hxSignTrackConfigure")
@Api(tags = "轨迹信息")
@Slf4j
public class HxSignTrackConfigureControlle {

    @Autowired
    private HxSignTrackConfigureService hxSignTrackConfigureService;


    /**
     * @return
     */
    @ApiOperation(value = "查询历史", notes = "")
    @PostMapping(value = "/getHxSignTrackConfigureList")
    public ResultVO<HxSignTrackConfigure> getHxSignTrackConfigureList(@RequestBody HxSignTrackConfigureQuery hxSignTrackConfigureQuery) {

        IPage<HxSignTrackConfigure> pageList = hxSignTrackConfigureService.getHxSignTrackConfigureList(hxSignTrackConfigureQuery);
        return ResponseUtil.success(pageList);
    }


    /**
     * @return
     */
    @ApiOperation(value = "保存轨迹", notes = "")
    @PostMapping(value = "/saveOrUpdateHxSignTrackConfigure")
    public ResultVO saveOrUpdateHxSignTrackConfigure(@Validated @RequestBody HxSignTrackConfigureSaveQuery hxSignTrackConfigureSaveQuery, BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("保存轨迹,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }
        int count = hxSignTrackConfigureService.saveOrUpdateHxSignTrackConfigure(hxSignTrackConfigureSaveQuery);
        return ResponseUtil.success(count);
    }


}
