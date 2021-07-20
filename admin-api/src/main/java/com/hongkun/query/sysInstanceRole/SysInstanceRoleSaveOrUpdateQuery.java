package com.hongkun.query.sysInstanceRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 标准角色
 *
 * @author fengxiaoyang
 * @date 2021-1-18 15:32:44
 */
@Data
@ApiModel("实例化角色-保存或者修改-请求参数")
public class SysInstanceRoleSaveOrUpdateQuery implements Serializable {

	@ApiModelProperty(value = "实例化角色id" )
	private String instanceRoleId;

	@ApiModelProperty(value = "实例化角色名称" )
	private String instanceRoleName;

	@ApiModelProperty(value = "实例化角色父级id" )
	private String instanceParentRoleId;

	@ApiModelProperty(value = "实例化角色父级id" )
	private String instanceParentRoleName;

	@ApiModelProperty(value = "null" )
	private Integer flowCode;

	@ApiModelProperty(value = "组织id" )
	private String orgId;

	@ApiModelProperty(value = "组织名称" )
	private String orgFullName;

	@ApiModelProperty(value = "公司id" )
	private String companyId;

	@ApiModelProperty(value = "项目id" )
	private String projectId;

	@ApiModelProperty(value = "标准角色id" )
	private String normRoleId;

	@ApiModelProperty(value = "标准角色名称" )
	private String normRoleName;

	@ApiModelProperty(value = "标准角色类型id" )
	private String normRoleType;

	@ApiModelProperty(value = "标准角色父级id" )
	private String normParentRoleId;

	@ApiModelProperty(value = "标准角色父级名称" )
	private String normParentRoleName;

	@ApiModelProperty(value = "状态id" )
	private Integer status;



}
