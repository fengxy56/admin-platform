package com.hongkun.until;

import com.hongkun.commons.ResultEnum;
import com.hongkun.model.vo.ResultVO;

/**
 * @Auther: chaizi
 */
public class ResponseUtil {
    /**
     * 错误返回
     *
     * @param resultEnum
     * @return
     */
    public static ResultVO error(ResultEnum resultEnum) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(resultEnum.getCode());
        resultVO.setErrMsg(resultEnum.getErrMsg());
        return resultVO;
    }

    /**
     * 自定义返回
     *
     * @param code
     * @param errMsg
     * @return
     */
    public static ResultVO error(int code, String errMsg) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setErrMsg(errMsg);
        return resultVO;
    }

    /**
     * 有参成功返回
     *
     * @param data
     * @return
     */
    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setErrMsg(ResultEnum.SUCCESS.getErrMsg());
        resultVO.setData(data);
        return resultVO;
    }

    /**
     * 无参成功返回
     *
     * @return
     */
    public static ResultVO success() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnum.SUCCESS.getCode());
        resultVO.setErrMsg(ResultEnum.SUCCESS.getErrMsg());
        return resultVO;
    }
}
