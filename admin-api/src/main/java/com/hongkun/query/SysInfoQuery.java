package com.hongkun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@ApiModel("接口系统信息")
public class SysInfoQuery {


    @ApiModelProperty(value = "系统id", example = "1001")
    private String id;

    @ApiModelProperty(value = "系统编码", example = "hxopensys-test")
    private String sysCode;

    @ApiModelProperty(value = "系统名称", example = "bpm流程统一平台")
    private String sysName;

    @ApiModelProperty(value = "系统状态", example = "状态")
    private Integer status;

}
