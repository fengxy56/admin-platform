package com.hongkun.bean.worko;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 同事圈账户
 *
 * @author fengxiaoyang
 * @date 2020-9-27 9:28:16
 */
@Data
@ApiModel("同事圈账户")
@TableName("users")
public class Users implements Serializable {

	@TableId(type = IdType.AUTO)
  	@ApiModelProperty(value = "主键id" )
	private Integer id;

  	@ApiModelProperty(value = "邮箱" )
	private String email;

  	@ApiModelProperty(value = "null" )
	private String region;

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


}
