package com.hongkun.exception;

import com.hongkun.commons.ResultEnum;
import lombok.Getter;

/**
 * @author chaixuhong
 * @date 2019-08-12
 */
@Getter
public class GlobalException extends RuntimeException {
    private int code;

    public GlobalException(ResultEnum resultEnum) {
        super(resultEnum.getErrMsg());
        this.code = resultEnum.getCode();
    }

    public GlobalException(int code, String errMsg) {
        super(errMsg);
        this.code = code;
    }
}
