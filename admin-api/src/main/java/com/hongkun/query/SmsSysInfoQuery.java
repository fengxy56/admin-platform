package com.hongkun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel("短信系统-请求参数")
public class SmsSysInfoQuery {


    @ApiModelProperty(value = "系统id", example = "1001")
    private String id;

    @ApiModelProperty(value = "短信编码", example = "bpm")
    private String smsKey;

    @ApiModelProperty(value = "短信密钥", example = "bpm")
    private String smsSecre;

    @ApiModelProperty(value = "系统名称", example = "bpm流程统一平台")
    private String sysName;

    @ApiModelProperty(value = "短信是否开启", example = "0")
    private Integer smsPush;

    @ApiModelProperty(value = "鸿信通知是否开启", example = "0")
    private Integer hxPush;

    @ApiModelProperty(value = "系统状态", example = "0")
    private Integer status;

    @ApiModelProperty(value = "短信字典id", example = "536296c4da00fe667ca38b40de3c2b40")
    private String smsDictId;

    @ApiModelProperty(value = "鸿信通知字典id", example = "536296c4da00fe667ca38b40de3c2b40")
    private String hxNotifyAppDictId;

}
