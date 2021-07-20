package com.hongkun.controller.hx;

import com.hongkun.model.hx.HxUserUndoStatusModel;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusBatchUpdateQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusUpdateQuery;
import com.hongkun.service.hx.HxUserUndoStatusService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.page.PageInfoVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
 * @ClassName HxUserUndoStatusController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:58
 */
@RestController
@RequestMapping("/hxUserUndoStatus")
@Api(tags = "鸿信待办")
@Slf4j
public class HxUserUndoStatusController {

    @Autowired
    private HxUserUndoStatusService hxUserUndoStatusService;

    /**
     * @return
     */
    @ApiOperation(value = "获取鸿信待办", notes = "")
    @PostMapping(value = "/getHxUserUndoStatusList")
    public ResultVO<HxUserUndoStatusModel> getHxUserUndoStatusList(@RequestBody HxUserUndoStatusQuery hxUserUndoStatusQuery) {

        long start = System.currentTimeMillis();


        List<HxUserUndoStatusModel> list = hxUserUndoStatusService.findUndoList(hxUserUndoStatusQuery);
        Integer totalCount = hxUserUndoStatusService.countUndoList(hxUserUndoStatusQuery);

        PageInfoVO<HxUserUndoStatusModel> pageInfoVO = new PageInfoVO();
        pageInfoVO.setRecords(list);
        pageInfoVO.setCurrent(hxUserUndoStatusQuery.getPageNo());//当前页面
        pageInfoVO.setSize(hxUserUndoStatusQuery.getPageSize());//当前页的数量

        Integer totalPage = totalCount % hxUserUndoStatusQuery.getPageSize() == 0 ? totalCount / hxUserUndoStatusQuery.getPageSize() : totalCount / hxUserUndoStatusQuery.getPageSize() + 1;
        pageInfoVO.setPages(totalPage);//总页数
        pageInfoVO.setTotal(totalCount);//总数量

        long end = System.currentTimeMillis();

        log.info("获取鸿信待办getHxUserUndoStatusList接口：" + hxUserUndoStatusQuery.getAccount() + ",用时：" + (end - start) + "ms");

        return ResponseUtil.success(pageInfoVO);

    }

    @ApiOperation(value = "更新鸿信待办", notes = "")
    @PostMapping(value = "/updateHxUserUndoStatus")
    public ResultVO updateHxUserUndoStatus(@Validated @RequestBody HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery,
                                           BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        if (StringUtils.isBlank(hxUserUndoStatusUpdateQuery.getId()) || StringUtils.isBlank(hxUserUndoStatusUpdateQuery.getTodoStatus())) {
            return ResponseUtil.error(0, "参数不能为空");
        }

        hxUserUndoStatusService.updateHxUndoStatus(hxUserUndoStatusUpdateQuery);

        return ResponseUtil.success();

    }


    @ApiOperation(value = "批量更新鸿信待办", notes = "")
    @PostMapping(value = "/batchUpdateHxUserUndoStatus")
    public ResultVO batchUpdateHxUserUndoStatus(@Validated @RequestBody HxUserUndoStatusBatchUpdateQuery hxUserUndoStatusBatchUpdateQuery,
                                                BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("批量更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }


        List<HxUserUndoStatusUpdateQuery> list = hxUserUndoStatusBatchUpdateQuery.getBatchList();
        for (HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery : list) {
            if (hxUserUndoStatusUpdateQuery != null && StringUtils.isNotBlank(hxUserUndoStatusUpdateQuery.getId())) {
                hxUserUndoStatusUpdateQuery.setDelete(hxUserUndoStatusBatchUpdateQuery.isDelete());
                hxUserUndoStatusUpdateQuery.setTodoStatus(hxUserUndoStatusBatchUpdateQuery.getTodoStatus());
                hxUserUndoStatusService.updateHxUndoStatus(hxUserUndoStatusUpdateQuery);
            }
        }

        return ResponseUtil.success();

    }

}
