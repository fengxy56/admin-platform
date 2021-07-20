package com.hongkun.query.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("角色授权请求参数")
public class SysAuthMenuQuery {

    @ApiModelProperty(value = "菜单id")
    private List<String> menuIdList;

    @NotBlank(message = "权限id不能为空")
    @ApiModelProperty(value = "权限id", example = "DCJT0118")
    private String authId;

    @NotBlank(message = "权限类型不能为空")
    @ApiModelProperty(value = "权限类型", example = "1账户,2角色")
    private String authType;

    @NotBlank(message = "系统类型不能为空")
    @ApiModelProperty(value = "系统类型", example = "fengxiaoyang")
    private String sysType;

    @ApiModelProperty(value = "系统名称", example = "fengxiaoyang")
    private String sysName;


}
