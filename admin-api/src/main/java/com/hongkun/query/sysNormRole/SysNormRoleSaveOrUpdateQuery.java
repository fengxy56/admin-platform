package com.hongkun.query.sysNormRole;

import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel("标准角色-保存或者修改-请求参数")
public class SysNormRoleSaveOrUpdateQuery implements Serializable {

  	@ApiModelProperty(value = "标准角色id" )
	private String normRoleId;

  	@ApiModelProperty(value = "标准角色名称" )
	private String normRoleName;

  	@ApiModelProperty(value = "标准角色短名称" )
	private String normRoleShortName;

  	@ApiModelProperty(value = "角色类型" )
	private String normRoleType;

  	@ApiModelProperty(value = "角色编码" )
	private String normRoleCode;

  	@ApiModelProperty(value = "标准流程排序" )
	private Integer flowCode;

  	@ApiModelProperty(value = "标准角色层级名称" )
	private String normRoleLevelName;

  	@ApiModelProperty(value = "标准角色层级" )
	private String normRoleLevel;

  	@ApiModelProperty(value = "标准角色父级id" )
	private String normParentRoleId;

  	@ApiModelProperty(value = "标准角色父级名称" )
	private String normParentRoleName;

  	@ApiModelProperty(value = "备注" )
	private String remarks;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "修改时间" )
	private Date updateTime;

  	@ApiModelProperty(value = "状态：0禁用，1启用" )
	private Integer status;

  	@ApiModelProperty(value = "组织id" )
	private String orgId;

  	@ApiModelProperty(value = "组织名称" )
	private String orgName;

	@ApiModelProperty(value = "组织长名称" )
	private String orgLongName;

  	@ApiModelProperty(value = "创建人" )
	private String createAccout;

  	@ApiModelProperty(value = "修改人" )
	private Date updateAccount;


}
