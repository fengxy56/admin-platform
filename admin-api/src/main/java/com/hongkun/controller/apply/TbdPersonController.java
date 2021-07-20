package com.hongkun.controller.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.hx.TBdPerson;
import com.hongkun.query.hx.TBdPersonQuery;
import com.hongkun.query.hx.TBdPersonUpdateQuery;
import com.hongkun.service.hxopensys.TBdPersonService;
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
 * @ClassName TbdPersonController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 8:26
 */

@RestController
@RequestMapping("/tbdPerson")
@Api(tags = "鸿信人员信息")
@Slf4j
public class TbdPersonController {

    @Autowired
    private TBdPersonService tBdPersonService;

    /**
     * @return
     */
    @ApiOperation(value = "获取鸿信人员信息", notes = "")
    @PostMapping(value = "/getTBdPersonList")
    public ResultVO getTBdPersonList(@RequestBody TBdPersonQuery tBdPersonQuery) {

        IPage<TBdPerson> list = tBdPersonService.getTBdPersonList(tBdPersonQuery);
        return ResponseUtil.success(list);
    }


    /**
     * @return
     */
    @ApiOperation(value = "修改更新鸿信人员信息", notes = "")
    @PostMapping(value = "/updateTBdPersonLastUpdateTime")
    public ResultVO updateTBdPersonLastUpdateTime(@Validated @RequestBody TBdPersonUpdateQuery tBdPersonUpdateQuery,
                                                  BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("修改更新鸿信人员信息接口,错误参数" + errorList.get(0).getDefaultMessage());
            return error(0, errorList.get(0).getDefaultMessage());
        }

        if (tBdPersonUpdateQuery != null && tBdPersonUpdateQuery.getStatus() != null && tBdPersonUpdateQuery.getStatus() == 0) {
            tBdPersonService.updateTBdPersonLastUpdateTime(tBdPersonUpdateQuery);
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(0, "除了禁用状态其它状态不可以修改时间");
        }


    }

}



