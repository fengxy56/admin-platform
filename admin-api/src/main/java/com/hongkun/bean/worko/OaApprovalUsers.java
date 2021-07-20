package com.hongkun.bean.worko;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: oa审批-同事圈账户
 *
 * @author fengxiaoyang
 * @date 2020-12-28 16:05:44
 */
@Data
@ApiModel("oa审批-同事圈账户")
@TableName("oa_approval_users")
public class OaApprovalUsers implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private Integer id;

  	@ApiModelProperty(value = "邮箱" )
	private String email;

  	@ApiModelProperty(value = "null" )
	private String region;

  	@ApiModelProperty(value = "手机号" )
	private String telphone;

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

  	@ApiModelProperty(value = "组织备注" )
	private String regionAll;

  	@ApiModelProperty(value = "暂无使用" )
	private String jobGradeName;

  	@ApiModelProperty(value = "创建时间" )
	private String createTime;

  	@ApiModelProperty(value = "是否删除" )
	private Integer isDelete;

  	@ApiModelProperty(value = "修改时间" )
	private String updateTime;

  	@ApiModelProperty(value = "审批原因" )
	private String approvalReason;

  	@ApiModelProperty(value = "修改人" )
	private Date updateAccount;


}
