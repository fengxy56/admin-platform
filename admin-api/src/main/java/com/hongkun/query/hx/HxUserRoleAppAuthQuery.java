package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户角色授权请求参数")
public class HxUserRoleAppAuthQuery {

    @ApiModelProperty(value = "菜单id")
    private List<String> menuIdList;

    @ApiModelProperty(value = "授权id", example = "DCJT0118")
    private String authId;

    @ApiModelProperty(value = "权限类型,1用户,2角色", example = "1")
    private Integer authType;

}
