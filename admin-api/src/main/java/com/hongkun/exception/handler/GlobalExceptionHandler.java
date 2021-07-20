package com.hongkun.exception.handler;


import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.AuthException;
import com.hongkun.exception.GlobalException;
import com.hongkun.until.ResponseUtil;
import com.hongkun.until.CommonUntil;
import com.hongkun.until.UrlUtil;
import com.hongkun.model.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chaixuhong
 * @date 2019-08-12
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResultVO handler(HttpServletRequest request, Exception e) {
        if (e instanceof AuthException) {
            AuthException authException = (AuthException) e;
            log.error("【认证异常】: err = {} url = {}", e.getMessage(), CommonUntil.getLogReplace(UrlUtil.getRequestUrl(request)));
            return ResponseUtil.error(authException.getCode(), e.getMessage());
        } else if (e instanceof GlobalException) {
            GlobalException globalException = (GlobalException) e;
            log.error("【全局自定义异常】: err = {} url = {}", e.getMessage(), CommonUntil.getLogReplace(UrlUtil.getRequestUrl(request)));
            return ResponseUtil.error(globalException.getCode(), e.getMessage());
        } else {
            e.printStackTrace();
            log.error("【未捕获异常】: err = {} url = {}", e.getMessage(), CommonUntil.getLogReplace(UrlUtil.getRequestUrl(request)));
            return ResponseUtil.error(ResultEnum.SERVER_ERROR);
        }
    }
}
