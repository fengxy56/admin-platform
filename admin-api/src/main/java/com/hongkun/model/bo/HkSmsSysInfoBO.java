package com.hongkun.model.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@ApiModel("短信系统信息")
public class HkSmsSysInfoBO {


    @ApiModelProperty(value = "主键id", example = "17620035797")
    private int sysId;

    @ApiModelProperty(value = "主键编码", example = "1")
    private String sysCode;

    @ApiModelProperty(value = "系统名称", example = "17620035797")
    private String sysName;

    @ApiModelProperty(value = "短信是否开启", example = "0启用，1禁用")
    private String smsPush;

    @ApiModelProperty(value = "鸿信是否开启", example = "0启用，1禁用")
    private String hxPush;

    @ApiModelProperty(value = "系统是否开启", example = "0启用，1禁用")
    private String deleteStatus;

    @ApiModelProperty(value = "鸿信轻应用appid", example = "1011204")
    private String hxNotifyAppid;

    @ApiModelProperty(value = "鸿信轻应用名称", example = "鸿坤IT")
    private String hxNotifyAppName;


    @ApiModelProperty(value = "修该人员账户", example = "admin")
    private String editUserName;

    @ApiModelProperty(value = "修该人员", example = "系统管理员")
    private String editName;


    @ApiModelProperty(value = "创建时间", example = "2020-02-24 14:16:51")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;

    @ApiModelProperty(value = "修改时间", example = "2020-02-26 14:16:51")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;


}
