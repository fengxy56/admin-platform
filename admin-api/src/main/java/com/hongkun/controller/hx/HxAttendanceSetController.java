package com.hongkun.controller.hx;

import com.hongkun.model.hx.HxAttendanceSetModel;
import com.hongkun.query.hx.mongodb.HxAttendanceSetQuery;
import com.hongkun.query.hx.mongodb.HxAttendanceSetUpdateQuery;
import com.hongkun.service.hx.HxAttendanceSetService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.page.PageInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName HxAttendanceSetController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 14:19
 */
@RestController
@RequestMapping("/hxAttendanceSet")
@Api(tags = "鸿信签到信息")
@Slf4j
public class HxAttendanceSetController {


    @Autowired
    private HxAttendanceSetService hxAttendanceSetService;


    /**
     * @return
     */
    @ApiOperation(value = "获取鸿信签到信息", notes = "")
    @PostMapping(value = "/getHxAttendanceSetList")
    public ResultVO<HxAttendanceSetModel> getHxAttendanceSetList(@RequestBody HxAttendanceSetQuery hxAttendanceSetQuery) {


        List<HxAttendanceSetModel> list = hxAttendanceSetService.getHxAttendanceSetList(hxAttendanceSetQuery);
        Integer totalCount = hxAttendanceSetService.countHxAttendanceSet(hxAttendanceSetQuery);

        PageInfoVO<HxAttendanceSetModel> pageInfoVO = new PageInfoVO();
        pageInfoVO.setRecords(list);
        pageInfoVO.setCurrent(hxAttendanceSetQuery.getPageNo());//当前页面
        pageInfoVO.setSize(hxAttendanceSetQuery.getPageSize());//当前页的数量

        Integer totalPage = totalCount % hxAttendanceSetQuery.getPageSize() == 0 ? totalCount / hxAttendanceSetQuery.getPageSize() : totalCount / hxAttendanceSetQuery.getPageSize() + 1;
        pageInfoVO.setPages(totalPage);//总页数
        pageInfoVO.setTotal(totalCount);//总数量


        return ResponseUtil.success(pageInfoVO);

    }


    /**
     * @return
     */
    @ApiOperation(value = "根据主键id获取签到信息", notes = "")
    @GetMapping(value = "/getHxAttendanceSetById")
    public ResultVO<HxAttendanceSetModel> getHxAttendanceSetById(String id) {

        HxAttendanceSetModel hxAttendanceSetModel = hxAttendanceSetService.getHxAttendanceSetById(id);

        return ResponseUtil.success(hxAttendanceSetModel);
    }


    /**
     * @return
     */
    @ApiOperation(value = "根据主键id修改签到信息", notes = "")
    @PostMapping(value = "/updateHxAttendanceSetById")
    public ResultVO updateHxAttendanceSetById(@Validated @RequestBody HxAttendanceSetUpdateQuery hxAttendanceSetUpdateQuery
            , BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("修改签到信息,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        hxAttendanceSetService.updateHxAttendanceSetById(hxAttendanceSetUpdateQuery);

        return ResponseUtil.success();
    }


}
