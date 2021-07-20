package com.hongkun.query.sysAuthMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel("角色授权请求参数")
public class SysAuthMenuQuery {


    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;


    @ApiModelProperty(value = "菜单id", example = "DCJT0118")
    private String menuId;


    @ApiModelProperty(value = "权限id", example = "1账户,2角色")
    private String authId;

    @ApiModelProperty(value = "权限类型", example = "1账户,2角色")
    private String authType;


}
