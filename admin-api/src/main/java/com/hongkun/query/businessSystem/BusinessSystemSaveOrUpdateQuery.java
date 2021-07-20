package com.hongkun.query.businessSystem;

import com.baomidou.mybatisplus.annotation.TableName;
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
@ApiModel("业务系统保存或者修改-请求参数")
public class BusinessSystemSaveOrUpdateQuery implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

	@ApiModelProperty(value = "公司名称" )
	private String businessSystemName;

	@ApiModelProperty(value = "公司名称简称" )
	private String businessSystemShortName;

	@ApiModelProperty(value = "公司编码" )
	private String businessSystemCode;

	@ApiModelProperty(value = "管理员账户" )
	private String adminAccount;

  	@ApiModelProperty(value = "状态是否可以用,0禁用,1启用" )
	private Integer status;

  	@ApiModelProperty(value = "排序" )
	private Integer showOrder;

  	@ApiModelProperty(value = "备注" )
	private String remark;


}
