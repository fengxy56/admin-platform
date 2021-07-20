package com.hongkun.controller;

import HongKun.EAC.SSO.AppSSOBLL;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.hongkun.bean.apply.UvEroleUserView;
import com.hongkun.commons.cache.TokenCache;
import com.hongkun.commons.Constants;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;

import com.hongkun.mapper.apply.UvEroleUserViewMapper;
import com.hongkun.until.AESUtil;
import com.hongkun.until.CookieUtil;
import com.hongkun.until.DateUtil;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.util.Strings;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author chaixuhong
 * @date 2020年3月25日09:40:28
 * 单点登陆
 */
@Controller
@RequestMapping("/sso")
@ApiIgnore
public class SsoLoginController {

    @Value("${hk-sso.url}")
    private String SSOURL;

    @Value("${hk-sso.ias.id}")
    private String IASID;

    @Value("${hk-sso.ias.key}")
    private String IASKey;

    @Value("${cookie.domain}")
    private String domain;

    @Value("${cookie.maxAge}")
    private Integer maxAge;

    @Autowired
    private TokenCache tokenCache;

    @Resource
    private UvEroleUserViewMapper uvEroleUserViewMapper;

    /**
     * sso登录
     *
     * @param resp
     * @param returnUrl
     */
    @ApiOperation(value = "sso登录", notes = "sso登录")
    @GetMapping("/login")
    public void login(HttpServletResponse resp, String returnUrl) throws IOException {
        if (Strings.isBlank(returnUrl)) {
            throw new GlobalException(ResultEnum.PARAM_ERROR);
        }
        String timeStamp = DateUtil.getCurrent();
        String html = AppSSOBLL.PostString1(IASID, timeStamp, returnUrl, null, SSOURL, IASKey);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(html);
        resp.getWriter().flush();
        resp.getWriter().close();
    }

    /**
     * 认证成功后的回调地址
     * redirectUrl需要base64编码
     *
     * @param resp
     * @param redirectUrl
     * @param UserAccount
     * @param Result
     * @return
     */
    @PostMapping("/redirect/{redirectUrl}")
    @ApiOperation(value = "验证返回", notes = "验证返回")
    public RedirectView redirect(HttpServletResponse resp, @PathVariable("redirectUrl") String redirectUrl, String UserAccount
            , String Result) {
        QueryWrapper<UvEroleUserView> queryWrapper = new QueryWrapper();
        queryWrapper.eq("accountName", UserAccount);
        queryWrapper.eq("statusType", 0);
        List<UvEroleUserView> users = uvEroleUserViewMapper.selectList(queryWrapper);
        UvEroleUserVO eroleUserVO = new UvEroleUserVO();
        List<String> bRoleIds = Lists.newArrayList();
        List<String> bRoleNames = Lists.newArrayList();
        List<String> bRoleTypes = Lists.newArrayList();
        for (int i = 0; i < users.size(); i++) {
            UvEroleUserView item = users.get(i);
            bRoleIds.add(item.getBRoleId());
            bRoleNames.add(item.getBRoleName());
//            bRoleTypes.add(item.getBRoleType());
            if (i == 0) {
                eroleUserVO.setAccountName(item.getAccountName());
                eroleUserVO.setChsName(item.getChsName());
//                eroleUserVO.setCompanyId(item.getCompanyId());
                eroleUserVO.setUserId(item.getUserId());
                eroleUserVO.setOrgId(item.getOrgId());
                eroleUserVO.setOrgName(item.getOrgName());
//                eroleUserVO.setProjectId(item.getProjectId());
                eroleUserVO.setOrgId(item.getOrgId());
            }
        }
        eroleUserVO.setBRoleId(bRoleIds);
        eroleUserVO.setBRoleName(bRoleNames);
        eroleUserVO.setBRoleType(bRoleTypes);
        String tokenKey = AESUtil.encrypt(String.format("%s-%s", UserAccount, DateUtil.getCurrentDate()), AESUtil.password);
        tokenCache.setToken(tokenKey, eroleUserVO);
        CookieUtil.set(resp, Constants.COOKIE_KEY, tokenKey, maxAge, domain);
        return new RedirectView(new String(Base64.decode(redirectUrl)));
    }


    /**
     * sso退出登录
     */
    @ApiOperation(value = "sso退出登录", notes = "sso退出登录")
    @GetMapping("/loginOut")
    public void loginOut(HttpServletResponse resp, String returnUrl) throws IOException {
        if (Strings.isBlank(returnUrl)) {
            throw new GlobalException(ResultEnum.PARAM_ERROR);
        }
        String timeStamp = DateUtil.getCurrent();
        //清楚cookie
        CookieUtil.set(resp, Constants.COOKIE_KEY, "", 0, domain);
        String html = AppSSOBLL.PostString1(IASID, timeStamp, returnUrl, null, SSOURL + "?logintype=loginout", IASKey);
        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().write(html);
        resp.getWriter().flush();
        resp.getWriter().close();
    }
}
