package com.hongkun.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 系统组织
 *
 * @author fengxiaoyang
 * @date 2021-1-17 11:42:32
 */
@Data
@ApiModel("系统组织")
@TableName("sys_org")
public class SysOrg implements Serializable {

  	@ApiModelProperty(value = "ID" )
	private String id;

  	@ApiModelProperty(value = "父组织" )
	private String parentId;

  	@ApiModelProperty(value = "null" )
	private String parentName;

  	@ApiModelProperty(value = "名称" )
	private String orgName;

  	@ApiModelProperty(value = "组织长名称" )
	private String orgLongName;

  	@ApiModelProperty(value = "编码" )
	private Integer orgNumber;

  	@ApiModelProperty(value = "长编码" )
	private String orgLongNumber;

  	@ApiModelProperty(value = "层级类型" )
	private Integer levelType;

  	@ApiModelProperty(value = "组织类型 0虚组织，1实组织，2未分配组织" )
	private Integer orgType;

  	@ApiModelProperty(value = "是否启用：0禁用，1启用" )
	private Integer status;

  	@ApiModelProperty(value = "排序" )
	private Integer showOrder;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "最后更新时间" )
	private Date lastUpdateTime;

  	@ApiModelProperty(value = "是否外部伙伴：0否，1是，2全部" )
	private Integer isPartner;


}
