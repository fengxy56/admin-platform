package com.hongkun.query.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("角色授权请求参数")
public class UserXRoleAuthQuery {

    @ApiModelProperty(value = "菜单id")
    private List<String> menuIdList;

    @ApiModelProperty(value = "角色id", example = "DCJT0118")
    private String roleId;

    @ApiModelProperty(value = "用户账户", example = "fengxiaoyang")
    private String account;

    @ApiModelProperty(value = "角色id", example = "DCJT0118")
    private String authId;

    @ApiModelProperty(value = "用户账户", example = "fengxiaoyang")
    private String authType;

    @ApiModelProperty(value = "系统类型", example = "fengxiaoyang")
    private String sysType;

}
