package com.hongkun.controller;


import com.hongkun.bean.SysUser;
import com.hongkun.commons.Constants;
import com.hongkun.commons.cache.TokenCache;
import com.hongkun.model.vo.ResultVO;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import com.hongkun.query.admin.LoginQuery;
import com.hongkun.service.admin.SysUserService;
import com.hongkun.until.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("")
@Api(tags = "用户管理", value = "用户相关接口")
public class LoginController {

    @Value("${cookie.domain}")
    private String domain;

    @Value("${cookie.maxAge}")
    private Integer maxAge;

    @Resource
    private TokenCache tokenCache;

    @Resource
    private SysUserService sysUserService;


    @PostMapping("/login")
    @ApiOperation(value = "获取登录用户信息")
    public ResultVO login(HttpServletResponse response,@Validated @RequestBody  LoginQuery loginQuery,
                          BindingResult bindingResult) {

        //校验参数是否为空
        if (bindingResult.hasErrors()) {
            List<ObjectError> errorList = bindingResult.getAllErrors();
            log.error("更新鸿信待办,错误参数" + errorList.get(0).getDefaultMessage());
            return ResponseUtil.error(0, errorList.get(0).getDefaultMessage());
        }

        UvEroleUserVO eroleUserVO = new UvEroleUserVO();


        SysUser sysUser = sysUserService.getSysUserByAccount(loginQuery.getAccount());
        if(sysUser == null){
            return ResponseUtil.error(0,"账号不存在！");
        }else {

            try {
                String md5Password = MD5Util.getMD5(loginQuery.getPassword()+"123456");
                if(md5Password.equals(sysUser.getPassword())){
                    eroleUserVO.setAccountName(sysUser.getAccount());
                    eroleUserVO.setChsName(sysUser.getName());
                    String tokenKey = AESUtil.encrypt(String.format("%s-%s", loginQuery.getAccount(), DateUtil.getCurrentDate()), AESUtil.password);
                    tokenCache.setToken(tokenKey, eroleUserVO);
                    //设置cookie
                    CookieUtil.set(response, Constants.COOKIE_KEY, tokenKey, maxAge, domain);

                }else{
                    return ResponseUtil.error(0,"密码错误！");
                }
            } catch (MD5Util.MD5Exception e) {
                e.printStackTrace();
            }

        }
        return ResponseUtil.success(eroleUserVO);


    }

    @GetMapping("/loginOut")
    @ApiOperation(value = "获取登录用户信息")
    public ResultVO loginOut(HttpServletResponse response) {
        //清除cookie
        CookieUtil.set(response, Constants.COOKIE_KEY, null, 0, domain);
        return ResponseUtil.success();


    }

}
