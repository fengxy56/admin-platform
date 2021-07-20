package com.hongkun.aspect;


import com.hongkun.commons.Constants;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.cache.TokenCache;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.AuthException;
import com.hongkun.exception.GlobalException;
import com.hongkun.until.CookieUtil;
import com.hongkun.until.RequestUtil;
import com.hongkun.until.CommonUntil;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author chaixuhong
 * @apiNote 【AOP】接口请求url、参数、请求时间记录日志
 * @date 2019-08-12
 */
@Component
@Aspect
@Slf4j
public class AuthorizeAspect {

    private static final long SLOW_SQL_TIME = 3000;

    @Autowired
    private TokenCache tokenCache;

    @Pointcut("execution(public * com.hongkun.controller.*.*.*(..))) " +
            "&& !execution(public * com.hongkun.controller.SsoLoginController.*(..)))" +
            "&& !execution(public * com.hongkun.controller.sys.LoginController.*(..))) ")
    public void verify() {
    }


    /**
     * 【切点】请求前拦截与记录
     */
    @Before("verify()")
    public void doVerify(JoinPoint joinPoint) {
        long reqBegin = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录请求开始时间
        request.setAttribute("reqBegin", reqBegin);
        /**
         * 从cookie中获取用户登录状态
         */
        Cookie cookie = CookieUtil.get(request, Constants.COOKIE_KEY);

//        log.info();

        if (cookie != null) {
            String tokenKey = cookie.getValue();
            UvEroleUserVO loginUser = tokenCache.getToken(tokenKey);
            if (loginUser == null) {
                throw new AuthException(ResultEnum.COOKIE_MISSING);
            }
            UserSession.setUser(loginUser);
        } else {
            throw new GlobalException(ResultEnum.COOKIE_MISSING);
        }

//        String token = request.getHeader("token");
//        if (StringUtils.isBlank(token)) {
//            log.error("缺少token参数");
//            throw new AuthException(ResultEnum.COOKIE_MISSING);
//        }
//        UvEroleUserVO user = tokenCache.getToken(token);
//        if (user == null) {
//            log.error("【登录拦截】token参数错误或已过期，token={}", CommonUntil.getLogReplace(token));
//            //throw new AuthException(ResultEnum.TOKEN_IS_INVALID_ERROR);
//        }


//        String tokenKey = "FDFE95A38152F5826A9C05B2BA5AF788F80D903F324370E2A2DDA225DD21D110";
//        UvEroleUserVO loginUser = tokenCache.getToken(tokenKey);
//        UserSession.setUser(loginUser);

    }

    /**
     * 【切点】请求后拦截与记录
     */
    @After("verify()")
    public void afterVerify() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Long reqBegin = (Long) request.getAttribute("reqBegin");
        Long reqEnd = System.currentTimeMillis();
        if (reqEnd - reqBegin >= 5000) {
            log.warn("【请求时间大于3秒】：used time={},path={}", reqEnd - reqBegin, CommonUntil.getLogReplace(RequestUtil.getRequestUrl(request)));
        }
        UserSession.remove();
    }
}
