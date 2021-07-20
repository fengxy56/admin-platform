package com.hongkun.controller.admin;


import com.hongkun.commons.UserSession;
import com.hongkun.model.vo.apply.SysAuthMenuVO;
import com.hongkun.service.admin.SysAuthMenuService;
import com.hongkun.until.ResponseUtil;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.UserRoleMenuVO;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/user")
@Api(tags = "用户管理", value = "用户相关接口")
public class UserController {


    @Autowired
    private SysAuthMenuService sysAuthMenuService;

    @GetMapping("/getUserInfoStr")
    @ApiOperation(value = "获取登录用户信息")
    public String getUserInfoStr() {
        return "高兴";
    }


    @GetMapping("/getUserInfo")
    @ApiOperation(value = "获取登录用户信息")
    public ResultVO<UvEroleUserVO> getUserInfo() {
        UvEroleUserVO user = UserSession.getUser();
        return ResponseUtil.success(user);
    }

    @GetMapping(value = "/getUserLoginTreeMenu")
    @ApiOperation(value = "获取用户登录左侧菜单列表", notes = "")
    public ResultVO<UserRoleMenuVO> getUserLoginTreeMenu(HttpServletRequest request, HttpServletResponse response) {

        UvEroleUserVO user = UserSession.getUser();
        String account = user.getAccountName();
        List<SysAuthMenuVO> userRoleMenuVOList = new ArrayList<>();
        if("admin".equals(account)){
            userRoleMenuVOList = sysAuthMenuService.getAdminUserLoginTreeMenu();
        }else{
            userRoleMenuVOList = sysAuthMenuService.getUserLoginTreeMenu();
        }
        return ResponseUtil.success(userRoleMenuVOList);
    }

}
