package com.hongkun.bean.worko;

import com.baomidou.mybatisplus.annotation.TableField;
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
@ApiModel("同事圈账户-请求参数")
@TableName("users")
public class UsersQuery implements Serializable {


	@ApiModelProperty(value = "页码", example = "1")
	private int pageNo = 1;

	@ApiModelProperty(value = "条数", example = "10")
	private int pageSize = 10;

  	@ApiModelProperty(value = "手机号" )
	private String telphone;

  	@TableField(value = "true_name")
  	@ApiModelProperty(value = "姓名" )
	private String trueName;

  	@ApiModelProperty(value = "账户" )
	private String username;

  	@ApiModelProperty(value = "组织" )
	private String dept;

  	@ApiModelProperty(value = "是否删除" )
	private Integer isDelete;

}
