package com.hongkun.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.SysUser;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.query.admin.SysUserQuery;
import com.hongkun.query.admin.SysUserSaveQuery;
import com.hongkun.query.admin.SysUserUpdateSortQuery;
import com.hongkun.service.admin.SysUserService;
import com.hongkun.until.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName HxOrgController
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/30 8:15
 */

@RestController
@RequestMapping("/sysUser")
@Api(tags = "组织信息")
@Slf4j
public class SysUserController {

    @Resource
    private SysUserService sysUserService;


    @ApiOperation(value = "获取用户信息", notes = "")
    @PostMapping(value = "/getSysUserList")
    public ResultVO<SysUser> getSysUserList(@RequestBody SysUserQuery sysUserQuery) {

        IPage<SysUser> list = sysUserService.getSysUserList(sysUserQuery);

        return ResponseUtil.success(list);

    }

    @ApiOperation(value = "保存或者修改用户信息", notes = "")
    @PostMapping(value = "/saveSysUser")
    public ResultVO<Integer> saveSysUser(@Validated@RequestBody SysUserSaveQuery sysUserSaveQuery,
                                            BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        int count = sysUserService.saveSysUser(sysUserSaveQuery);
        return ResponseUtil.success(count);

    }

    @ApiOperation(value = "修改用户信息", notes = "")
    @PostMapping(value = "/updateSysUser")
    public ResultVO<Integer> updateSysUser(@Validated@RequestBody SysUserSaveQuery sysUserSaveQuery,
                                         BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        int count = sysUserService.updateSysUser(sysUserSaveQuery);
        return ResponseUtil.success(count);

    }


    @ApiOperation(value = "移动用户", notes = "")
    @PostMapping(value = "/moveSysUserSort")
    public ResultVO<Integer> moveSysUserSort(@Validated @RequestBody SysUserUpdateSortQuery sysUserUpdateSortQuery,
                                           BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        //上移
        if("moveUp".equals(sysUserUpdateSortQuery.getMoveType())){
            sysUserService.moveUpSysUserSort(sysUserUpdateSortQuery);
        }else if("moveDown".equals(sysUserUpdateSortQuery.getMoveType())){
            //下移
            sysUserService.moveDownSysUserSort(sysUserUpdateSortQuery);
        }
        return ResponseUtil.success();

    }



}
