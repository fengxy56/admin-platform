package com.hongkun.controller.hx;

import com.hongkun.query.hx.HxPollImageSaveOrUpdateQuery;
import com.hongkun.query.hx.HxPollImageVO;
import com.hongkun.service.hxlightapps.HxPollImageService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName HxPollImageController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 17:25
 */
@RestController
@RequestMapping("/hxPollImage")
@Api(tags = "轮播图")
@Slf4j
public class HxPollImageController {


    @Autowired
    private HxPollImageService hxPollImageService;

    /* 轮播图 - 获取轮播图数据 */
    @GetMapping(value = "/getHxPollImageList")
    @ApiOperation(value = "轮播图 - 获取轮播图数据", notes = "轮播图 - 获取轮播图数据")
    public ResultVO<List<HxPollImageVO>> getHxPollImageList(HttpServletRequest request) {

        List<HxPollImageVO> list = hxPollImageService.getHxPollImageList();
        return ResponseUtil.success(list);
    }


    /* 轮播图 - 获取轮播图数据 */
    @PostMapping(value = "/saveOrUpdateHxPollImage")
    @ApiOperation(value = "修改轮播图", notes = "")
    public ResultVO<List<HxPollImageVO>> saveOrUpdateHxPollImage(HttpServletRequest request, @RequestBody HxPollImageSaveOrUpdateQuery hxPollImageSaveOrUpdateQuery) {

        hxPollImageService.saveOrUpdateHxPollImage(hxPollImageSaveOrUpdateQuery);

        return ResponseUtil.success();

    }


}
