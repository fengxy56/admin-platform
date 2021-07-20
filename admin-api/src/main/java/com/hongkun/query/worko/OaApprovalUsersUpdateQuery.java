package com.hongkun.query.worko;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: oa审批-同事圈账户
 *
 * @author fengxiaoyang
 * @date 2020-12-28 16:05:44
 */
@Data
@ApiModel("oa审批-同事圈账户-请求参数")
public class OaApprovalUsersUpdateQuery implements Serializable {


  	@ApiModelProperty(value = "邮箱" )
	private String email;

  	@ApiModelProperty(value = "手机号" )
	private String telphone;

	@TableField(value = "true_name")
  	@ApiModelProperty(value = "姓名" )
	private String trueName;

  	@ApiModelProperty(value = "账户" )
	private String username;

  	@ApiModelProperty(value = "组织" )
	private String dept;

  	@ApiModelProperty(value = "头像" )
	private String headIcon;

  	@ApiModelProperty(value = "岗位" )
	private String jobs;

  	@ApiModelProperty(value = "组织id" )
	private String deptIds;

  	@ApiModelProperty(value = "是否删除" )
	private Integer isDelete;

  	@ApiModelProperty(value = "审批原因" )
	private String approvalReason;

  	@ApiModelProperty(value = "修改人" )
	private String updateAccount;


}
