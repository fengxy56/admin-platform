package com.hongkun.controller.worko;


import com.hongkun.bean.worko.Worko;
import com.hongkun.query.worko.WorkoQuery;
import com.hongkun.query.worko.WorkoUpdateQuery;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.page.PageInfoVO;
import com.hongkun.service.worko.WorkoService;
import com.hongkun.until.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @ClassName WorkoController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 16:25
 */
@RestController
@RequestMapping("/worko")
@Api(tags = "同事圈信息")
@Slf4j
public class WorkoController {


    @Resource
    private WorkoService workoService;

    /**
     * @return
     */
    @ApiOperation(value = "分页查询同事圈", notes = "")
    @PostMapping(value = "/getWorkoList")
    public ResultVO<Worko> getWorkoList(@RequestBody WorkoQuery workoQuery) {
        PageInfoVO<Worko> list = workoService.getWorkoList(workoQuery);

        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "修改同事圈是否置顶或者删除", notes = "")
    @PostMapping(value = "/updateWorko")
    public ResultVO<String> updateWorko(@RequestBody WorkoUpdateQuery workoUpdateQuery) {

        Integer count = workoService.updateWorko(workoUpdateQuery);
        return ResponseUtil.success(count);
    }

}
