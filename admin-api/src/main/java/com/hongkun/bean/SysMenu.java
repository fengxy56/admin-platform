package com.hongkun.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 菜单表
 *
 * @author fengxiaoyang
 * @date 2021-1-17 19:22:24
 */
@Data
@ApiModel("菜单表")
@TableName("sys_menu")
public class SysMenu implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

  	@ApiModelProperty(value = "菜单名称" )
	private String menuName;

  	@ApiModelProperty(value = "菜单编码" )
	private String menuCode;

  	@ApiModelProperty(value = "父级id" )
	private String parentId;

  	@ApiModelProperty(value = "菜单链接" )
	private String menuUrl;

  	@ApiModelProperty(value = "类型，0父级" )
	private Integer type;

  	@ApiModelProperty(value = "图片样式" )
	private String icon;

  	@ApiModelProperty(value = "状态是否可以用,0禁用,1启用" )
	private Integer status;

  	@ApiModelProperty(value = "排序" )
	private Integer showOrder;

  	@ApiModelProperty(value = "备注" )
	private String remark;

  	@ApiModelProperty(value = "null" )
	private Date createTime;

  	@ApiModelProperty(value = "null" )
	private Date updateTime;


}
