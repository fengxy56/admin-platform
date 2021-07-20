package com.hongkun.query.sysOrg;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysOrgSaveQuery
 * @Description 这里描述
 * @Author sys
 * @Date 2021/1/16 17:19
 */
@Data
@ApiModel("系统组织-保存组织-请求参数")
public class SysOrgUpdateQuery {

    @ApiModelProperty(value = "组织id")
    private String id;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "父级组织id")
    private String parentId;

    @ApiModelProperty(value = "父级组织名称")
    private String parentName;


}
