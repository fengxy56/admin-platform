package com.hongkun.controller.admin;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.SysMenu;
import com.hongkun.bean.apply.Menu;
import com.hongkun.service.admin.SysMenuService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sysMenu")
@Api(tags = "菜单信息")
@Slf4j
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;


    @GetMapping(value = "/getTreeXMenuList")
    @ApiOperation(value = "获取树状菜单集合", notes = "")
    public ResultVO<Menu> getTreeXMenuList() {
        return ResponseUtil.success(sysMenuService.getTreeXMenuList());
    }


    @GetMapping(value = "/getHkMenuList")
    @ApiOperation(value = "根据父级id获取菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页数", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "条数", example = "10"),
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "菜单父级id", example = "-1")
    })
    public ResultVO<IPage<Menu>> getXMenuList(Integer pageNo, Integer pageSize, String parentId) {

        if (pageNo == null || pageSize == null || StringUtils.isBlank(parentId)) {
            //throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
        }

        IPage<SysMenu> list = sysMenuService.getXMenuList(pageNo, pageSize, parentId);
        return ResponseUtil.success(list);
    }

    /**
     * @return
     */
    @ApiOperation(value = "获取字典组信息", notes = "获取系统信息")
    @PostMapping(value = "/saveOrUpdateHkMenu")
    public ResultVO saveOrUpdateHkMenu(@RequestBody SysMenu menu) {

        log.info("菜单信息,hkSysMenu:" + menu.toString());
        //去数据库中查询数据
        sysMenuService.saveOrUpdate(menu);
        return ResponseUtil.success(menu);
    }

    @GetMapping(value = "/getHkMenuLink")
    @ApiOperation(value = "获取所有末级菜单列表")
    public ResultVO<List<Menu>> getHkMenuLink() {

        return ResponseUtil.success(sysMenuService.getHkMenuLink());
    }

    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "菜单主键id,", example = "1"),
    })
    @GetMapping(value = "/deleteHkMenuById")
    @ApiOperation(value = "删除菜单", notes = "")
    public ResultVO<Integer> deleteXMenu(String id) {

        if (StringUtils.isBlank(id)) {
            //throw new GlobalException(ResultEnum.PARAM_NOT_NULL);
        }

        sysMenuService.deleteXMenuById(id);
        return ResponseUtil.success();

    }

}