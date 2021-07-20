package com.hongkun.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 系统人员
 *
 * @author fengxiaoyang
 * @date 2021-1-17 16:10:13
 */
@Data
@ApiModel("系统人员")
@TableName("sys_user")
public class SysUser implements Serializable {

  	@ApiModelProperty(value = "ID" )
	private String id;

  	@ApiModelProperty(value = "账户" )
	private String account;

  	@ApiModelProperty(value = "名称" )
	private String name;

  	@ApiModelProperty(value = "手机号" )
	private String phone;

  	@ApiModelProperty(value = "办公电话1" )
	private String officePhone;

  	@ApiModelProperty(value = "办公电话2" )
	private String officePhone2;

  	@ApiModelProperty(value = "md5加密，密码" )
	private String password;

  	@ApiModelProperty(value = "AES加密" )
	private String passwordAec;

  	@ApiModelProperty(value = "职位" )
	private String jobTitle;

  	@ApiModelProperty(value = "性别：0未知，1男，2女" )
	private Integer gender;

  	@ApiModelProperty(value = "头像URL" )
	private String photoUrl;

  	@ApiModelProperty(value = "邮箱" )
	private String email;

  	@ApiModelProperty(value = "部门ID" )
	private String orgId;

  	@ApiModelProperty(value = "部门名称" )
	private String orgName;

  	@ApiModelProperty(value = "组织编码长名称" )
	private String orgLongName;

  	@ApiModelProperty(value = "0：离职，1：正常，2：禁用" )
	private Integer status;

  	@ApiModelProperty(value = "生日" )
	private Date birthday;

  	@ApiModelProperty(value = "null" )
	private Integer isHidePhone;

  	@ApiModelProperty(value = "名称首字母拼音" )
	private String firstPinyin;

  	@ApiModelProperty(value = "名称全拼,没有空格" )
	private String fullPinyin;

  	@ApiModelProperty(value = "名称全拼,有空格" )
	private String fullPinyinSpace;

  	@ApiModelProperty(value = "null" )
	private Integer showOrder;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "最后更新时间" )
	private Date lastUpdateTime;

  	@ApiModelProperty(value = "用户类型：管理员：10，普通用户：20" )
	private Integer userType;

  	@ApiModelProperty(value = "组织用户类型：0表示普通用户，1表示部门负责人" )
	private Integer orgUserType;

  	@ApiModelProperty(value = "激活时间" )
	private Date activeTime;

  	@ApiModelProperty(value = "是否外部伙伴" )
	private Integer isPartner;


}
