package com.hongkun.controller.hx;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.mapproval.HongkunOutsupportinfo;
import com.hongkun.bean.mapproval.HongkunPushinfo;
import com.hongkun.model.vo.hx.HongkunPushinfoVO;
import com.hongkun.query.mapproval.HongkunPushinfoQuery;
import com.hongkun.query.mapproval.HxPushInfoBatchUpdateQuery;
import com.hongkun.query.mapproval.HxPushInfoUpdateQuery;
import com.hongkun.service.mapproval.HongkunPushinfoService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
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
 * @ClassName HongkunPushinfoController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 14:20
 */
@RestController
@RequestMapping("/hongkunPushinfo")
@Api(tags = "移动审批")
@Slf4j
public class HongkunPushinfoController {


    @Autowired
    private HongkunPushinfoService hongkunPushinfoService;

    /**
     * @return
     */
    @ApiOperation(value = "获取移动审批信息", notes = "")
    @PostMapping(value = "/getHongkunPushinfoList")
    public ResultVO<HongkunPushinfoVO> getHongkunPushinfoList(@RequestBody HongkunPushinfoQuery hongkunPushinfoQuery) {

        IPage<HongkunPushinfoVO> list = hongkunPushinfoService.getHongkunPushinfoList(hongkunPushinfoQuery);
        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "获取移动审批应用信息", notes = "")
    @PostMapping(value = "/getHongkunOutsupportinfoList")
    public ResultVO<HongkunOutsupportinfo> getHongkunOutsupportinfoList() {

        List<HongkunOutsupportinfo> list = hongkunPushinfoService.getHongkunOutsupportinfoList(null);
        return ResponseUtil.success(list);
    }


    /**
     * @return
     */
    @ApiOperation(value = "修改移动审批信息", notes = "")
    @PostMapping(value = "/updateHongkunPushinfo")
    public ResultVO updateHongkunPushinfo(
            @Validated @RequestBody HxPushInfoUpdateQuery hxPushInfoUpdateQuery,
            BindingResult bindingResult) {


        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("保存轨迹,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }


        if (hxPushInfoUpdateQuery.getId() == null) {
            return ResponseUtil.error(0, "参数不能为空");
        }


        hongkunPushinfoService.updateHongkunPushinfo(hxPushInfoUpdateQuery);

        return ResponseUtil.success();
    }

    @ApiOperation(value = "批量更新移动审批", notes = "")
    @PostMapping(value = "/batchUpdateHongkunPushinfo")
    public ResultVO batchUpdateHxUserUndoStatus(@Validated @RequestBody HxPushInfoBatchUpdateQuery hxPushInfoBatchUpdateQuery,
                                                BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("批量更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }


        List<HxPushInfoUpdateQuery> list = hxPushInfoBatchUpdateQuery.getBatchList();
        for (HxPushInfoUpdateQuery hxPushInfoUpdateQuery : list) {
            if (hxPushInfoUpdateQuery != null && StringUtils.isNotBlank(hxPushInfoUpdateQuery.getHxMsgId())) {

                hxPushInfoUpdateQuery.setDeleteStatus(hxPushInfoBatchUpdateQuery.getDeleteStatus());
                hxPushInfoUpdateQuery.setIfTodo(hxPushInfoBatchUpdateQuery.getIfTodo());

                hongkunPushinfoService.updateHongkunPushinfo(hxPushInfoUpdateQuery);
            }
        }

        return ResponseUtil.success();

    }

}
