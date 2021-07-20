package com.hongkun.query.sysAuthMenu;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@ApiModel("角色授权请求参数")
public class SysAuthMenuSaveOrUpdateQuery {

    @ApiModelProperty(value = "菜单id")
    private List<String> menuIdList;

    @NotBlank(message = "权限id不能为空")
    @ApiModelProperty(value = "权限id", example = "DCJT0118")
    private String authId;

    @NotBlank(message = "权限类型不能为空")
    @ApiModelProperty(value = "权限类型", example = "1账户,2角色")
    private String authType;

    @NotBlank(message = "业务系统id不能为空")
    @ApiModelProperty(value = "业务系统id", example = "fengxiaoyang")
    private String businessSystemId;

//    @NotBlank(message = "业务系统名称不能为空")
    @ApiModelProperty(value = "业务系统名称", example = "fengxiaoyang")
    private String businessSystemName;


}
