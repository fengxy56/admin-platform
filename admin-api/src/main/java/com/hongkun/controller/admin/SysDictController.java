/**
 *
 */
package com.hongkun.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SysDict;

import com.hongkun.query.SysDictSaveQuery;
import com.hongkun.service.admin.SysDictService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.sys.SysDictTreeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author fengxiaoyang
 * @date 2020年03月24日 上午10:57:35
 * @desc
 */
@Slf4j
@RestController
@RequestMapping("/sysDict")
@Api(tags = "字典管理", value = "字典管理")
public class SysDictController {

    @Autowired
    private SysDictService sysDictService;


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "pageNo", value = "页数", example = "1"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "条数", example = "10"),
            @ApiImplicitParam(paramType = "query", name = "dicGroup", value = "字典组", example = ""),
            @ApiImplicitParam(paramType = "query", name = "dicName", value = "字典组", example = ""),

    })
    @GetMapping(value = "/getTreeDict")
    @ApiOperation(value = "获取字典项树状", notes = "")
    public ResultVO<SysDictTreeVO> getTreeDict(Integer pageNo, Integer pageSize, String dicGroup, String dicName) {
        IPage<SysDictTreeVO> list = sysDictService.getSysDictTreeVO(pageNo, pageSize, dicGroup, dicName);
        return ResponseUtil.success(list);
    }


    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "parentId", value = "字典组", example = ""),
    })
    @GetMapping(value = "/getDictListByParentId")
    @ApiOperation(value = "根据父级id获取字典项集合", notes = "")
    public ResultVO<SysDict> getDictListByParentId(String parentId) {
        List<SysDict> list = sysDictService.getSysDictListByParentId(parentId);
        return ResponseUtil.success(list);
    }

    @PostMapping(value = "/saveOrUpdateDictInfo")
    @ApiOperation(value = "保存或者修改字典信息", notes = "")
    public ResultVO saveOrUpdateDictInfo(@RequestBody SysDictSaveQuery sysDictSaveQuery) {

        log.info("保存或者修改字典信息:" + sysDictSaveQuery.toString());

        int count = sysDictService.saveOrUpdateSysDict(sysDictSaveQuery);
        if (count > 0) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(-1, "保存或者修改失败");
        }

    }

}
