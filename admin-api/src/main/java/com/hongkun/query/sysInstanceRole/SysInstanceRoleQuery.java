package com.hongkun.query.sysInstanceRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysUserQuery
 * @Description 这里描述
 * @Author sys
 * @Date 2021/1/16 14:16
 */
@Data
@ApiModel("实例化角色-请求参数")
public class SysInstanceRoleQuery {


    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "组织id" )
    private String orgId;


    @ApiModelProperty(value = "标准角色id" )
    private String normRoleId;

    @ApiModelProperty(value = "标准角色名称" )
    private String normRoleName;

    @ApiModelProperty(value = "标准角色父级id" )
    private String normParentRoleId;

    @ApiModelProperty(value = "标准角色父级名称" )
    private String normParentRoleName;

    @ApiModelProperty(value = "状态：0无效，1有效" )
    private Integer status;

}
