package com.hongkun.query.businessSystem;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 系统公司表
 *
 * @author fengxiaoyang
 * @date 2021-2-3 9:03:46
 */
@Data
@ApiModel("业务系统分页查询-请求参数")
public class BusinessSystemQuery implements Serializable {

	@ApiModelProperty(value = "页码", example = "1")
	private Integer pageNo = 1;

	@ApiModelProperty(value = "条数", example = "10")
	private Integer pageSize = 10;

	@ApiModelProperty(value = "业务系统id" )
	private String id;

	@ApiModelProperty(value = "业务系统名称" )
	private String businessSystemName;

	@ApiModelProperty(value = "业务系统名称简称" )
	private String businessSystemShortName;

	@ApiModelProperty(value = "业务系统编码" )
	private String businessSystemCode;

	@ApiModelProperty(value = "管理员账户" )
	private String adminAccount;

  	@ApiModelProperty(value = "状态是否可以用,0禁用,1启用" )
	private Integer status;

  	@ApiModelProperty(value = "备注" )
	private String remark;




}
