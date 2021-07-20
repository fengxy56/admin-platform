package com.hongkun.bean.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 轻应用授权
 *
 * @author fengxiaoyang
 * @date 2020-11-30 17:57:27
 */
@Data
@ApiModel("轻应用授权")
@TableName("hx_app_menu_auth")
public class HxAppMenuAuth implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

  	@ApiModelProperty(value = "轻应用appid" )
	private Integer appId;

  	@ApiModelProperty(value = "权限id" )
	private String authId;

  	@ApiModelProperty(value = "权限类型,1用户,2角色" )
	private Integer authType;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "修改时间" )
	private Date updateTime;

  	@ApiModelProperty(value = "修改人员" )
	private String updateAccount;


}
