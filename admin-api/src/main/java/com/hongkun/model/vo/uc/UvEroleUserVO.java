package com.hongkun.model.vo.uc;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-26 9:39:37
 */
@Data
@ApiModel("用户角色对应表")
public class UvEroleUserVO implements Serializable {

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String chsName;

    @ApiModelProperty(value = "用户账号")
    private String accountName;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "公司id")
    private String companyId;

    @ApiModelProperty(value = "项目id")
    private String projectId;

    @ApiModelProperty(value = "标准角色id")
    private List<String> bRoleId;

    @ApiModelProperty(value = "标准角色名称")
    private List<String> bRoleName;

    @ApiModelProperty(value = "角色名称类型")
    private List<String> bRoleType;

    @ApiModelProperty(value = "实体公司id")
    private String companyEntitiesId = "8D6908B8-A244-46E3-B730-994B47852B77";

}
