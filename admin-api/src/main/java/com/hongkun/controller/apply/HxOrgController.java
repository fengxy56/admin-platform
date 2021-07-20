package com.hongkun.controller.apply;

import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.apply.HxOrgVO;
import com.hongkun.service.apply.HxOrgService;
import com.hongkun.until.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName HxOrgController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/30 8:15
 */

@RestController
@RequestMapping("/hxOrg")
@Api(tags = "组织信息")
@Slf4j
public class HxOrgController {

    @Resource
    private HxOrgService hxOrgService;


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @PostMapping(value = "/getHxOrgTreeList")
    public ResultVO<HxOrgVO> getHxOrgTreeList(@RequestBody SysAuthMenuQuery sysAuthMenuQuery) {
        List<HxOrgVO> list = hxOrgService.getHxOrgTreeList(sysAuthMenuQuery);

        return ResponseUtil.success(list);

    }


}
