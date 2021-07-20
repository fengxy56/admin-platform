package com.hongkun.model.vo.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("权限管理-用户角色关系返回")
public class UvBroleVO {

    @ApiModelProperty(value = "标准角色id")
    private String bRoleId;

    @ApiModelProperty(value = "标准角色名称")
    private String bRoleName;

    @ApiModelProperty(value = "标准角色类型")
    private String bRoleType;

    @ApiModelProperty(value = "标准角色父级名称")
    private String bParentRoleName;

    @ApiModelProperty(value = "发布状态.0已发布,1编制中")
    private Integer publishStatus;

}
