package com.hongkun.controller.admin;

import com.hongkun.model.admin.SysOrgVO;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.query.admin.SysOrgSaveQuery;
import com.hongkun.query.admin.SysOrgUpdateQuery;
import com.hongkun.query.admin.SysOrgUpdateSortQuery;
import com.hongkun.service.admin.SysOrgService;
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
@RequestMapping("/sysOrg")
@Api(tags = "组织信息")
@Slf4j
public class SysOrgController {

    @Resource
    private SysOrgService sysOrgService;


    @ApiOperation(value = "获取组织信息", notes = "获取组织信息")
    @GetMapping(value = "/getSysOrgTreeList")
    public ResultVO<SysOrgVO> getHxOrgTreeList() {

        List<SysOrgVO> list = sysOrgService.getSysOrgTreeList();

        return ResponseUtil.success(list);

    }

    @ApiOperation(value = "保存组织信息", notes = "")
    @PostMapping(value = "/saveSysOrg")
    public ResultVO<Integer> saveSysOrg(@RequestBody SysOrgSaveQuery sysOrgSaveQuery) {

        int count = sysOrgService.saveSysOrg(sysOrgSaveQuery);

        return ResponseUtil.success(count);

    }



    @ApiOperation(value = "修改组织信息", notes = "")
    @PostMapping(value = "/updateSysOrg")
    public ResultVO<Integer> updateSysOrg(@RequestBody SysOrgUpdateQuery sysOrgUpdateQuery) {

        int count = sysOrgService.updateSysOrg(sysOrgUpdateQuery);
        return ResponseUtil.success(count);
    }


    @ApiOperation(value = "移动组织", notes = "")
    @PostMapping(value = "/moveSysOrgSort")
    public ResultVO<Integer> moveSysOrgSort(@Validated @RequestBody SysOrgUpdateSortQuery sysOrgUpdateSortQuery,
                                             BindingResult bindingResult) {
        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        //上移
        if("moveUp".equals(sysOrgUpdateSortQuery.getMoveType())){
            sysOrgService.moveUpSysOrgSort(sysOrgUpdateSortQuery);
        }else if("moveDown".equals(sysOrgUpdateSortQuery.getMoveType())){
            //下移
            sysOrgService.moveDownSysOrgSort(sysOrgUpdateSortQuery);
        }
        return ResponseUtil.success();

    }

}
