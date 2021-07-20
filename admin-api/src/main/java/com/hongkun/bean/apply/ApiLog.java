package com.hongkun.bean.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author 王胜东--wangsd@t-ark.com
 * @date 2018年8月28日 上午10:38:05
 * @desc
 */

@Data
@TableName("API_LOG")
@ApiModel(value = "ApiLog", description = "系统日志信息")
public class ApiLog {

    @ApiModelProperty(name = "id", value = "ID")
    private String id;


    @ApiModelProperty(name = "userId", value = "用户ID")
    private String userId;


    @ApiModelProperty(name = "userName", value = "用户名称")
    private String userName;

    @ApiModelProperty(name = "logPathUrl", value = "请求路径")
    private String logPathUrl;

    @ApiModelProperty(name = "logAction", value = "请求方法")
    private String logAction;


    @ApiModelProperty(name = "logTargetType", value = "请求目标")
    private String logTargetType;


    @ApiModelProperty(name = "logRemark", value = "备注")
    private String logRemark;


    @ApiModelProperty(name = "logIp", value = "访问IP")
    private String logIp;


    @ApiModelProperty(name = "logTime", value = "访问时间")
    private Date logTime;


    @ApiModelProperty(name = "logError", value = "错误信息")
    private String logError;

}