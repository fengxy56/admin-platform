package com.hongkun.exception;


import com.hongkun.commons.ResultEnum;
import lombok.Getter;

/**
 * @author chaixuhong
 * @date 2019-08-12
 */
@Getter
public class AuthException extends RuntimeException {
    private int code;

    public AuthException(ResultEnum resultEnum) {
        super(resultEnum.getErrMsg());
        this.code = resultEnum.getCode();
    }

}
