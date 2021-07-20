/**
 *
 */
package com.hongkun.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;

import com.hongkun.bean.apply.UvEroleUserView;
import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.query.uc.UvERoleUserQuery;
import com.hongkun.model.vo.apply.SysAuthMenuVO;
import com.hongkun.service.admin.SysAuthMenuService;
import com.hongkun.service.apply.UvEroleUserViewService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;

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
 * @author fengxiaoyang
 * @date 2020年03月24日 上午10:57:35
 * @desc
 */
@RestController
@RequestMapping("/sysAuthSysMenu")
@Api(tags = "权限管理", value = "用户角色、角色列表")
@Slf4j
public class SysAuthSysMenuController {

    @Autowired
    private SysAuthMenuService sysAuthMenuService;

    @Autowired
    private UvEroleUserViewService uvEroleUserViewService;


    @ApiOperation(value = "获取用户角色关系信息", notes = "模糊查询获取用户信息")
    @PostMapping(value = "/getUvERoleUserList")
    public ResultVO<UvEroleUserView> getUvERoleUserList(@RequestBody UvERoleUserQuery uvERoleUserQuery) {

        log.info("获取用户角色关系信息,uvERoleUserQuery:" + uvERoleUserQuery.toString());

        IPage<UvEroleUserView> list = uvEroleUserViewService.getUvERoleUserViewList(uvERoleUserQuery);
        return ResponseUtil.success(list);

    }


    @PostMapping(value = "/getTreeSysAuthMenuList")
    @ApiOperation(value = "获取标准角色授权树状结构列表", notes = "")
    public ResultVO<SysAuthMenuVO> getTreeSysAuthMenuList(@RequestBody SysAuthMenuQuery sysAuthMenuQuery) {

        return ResponseUtil.success(sysAuthMenuService.getSysAuthMenuList(sysAuthMenuQuery));
    }


    @PostMapping(value = "/saveOrUpdateSysAuthMenu")
    @ApiOperation(value = "保存或者修改角色授权", notes = "")
    public ResultVO saveOrUpdateSysAuthMenu(@Validated @RequestBody SysAuthMenuQuery sysAuthMenuQuery, BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("保存轨迹,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        sysAuthMenuService.saveOrUpdateSysAuthMenu(sysAuthMenuQuery);
        return ResponseUtil.success();

    }


}
