package com.hongkun.query.sysOrg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName SysOrgSaveQuery
 * @Description 这里描述
 * @Author sys
 * @Date 2021/1/16 17:19
 */
@Data
@ApiModel("系统组织-保存组织-请求参数")
public class SysOrgUpdateSortQuery {

    @NotBlank(message = "组织id不能为空")
    @ApiModelProperty(value = "组织id")
    private String id;

    @NotBlank(message = "父级组织id不能为空")
    @ApiModelProperty(value = "父级组织id")
    private String parentId;


    @NotNull(message = "排序字段不能为空")
    @ApiModelProperty(value = "排序字段")
    private Integer showOrder;


    @NotBlank(message = "移动类型不能为空")
    @ApiModelProperty(value = "移动类型: moveUp上移动,moveDown下移")
    private String moveType;


}
