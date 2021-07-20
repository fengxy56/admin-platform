package com.hongkun.query.sysMenu;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 菜单表
 *
 * @author fengxiaoyang
 * @date 2021-2-23 9:58:53
 */
@Data
@ApiModel("菜单表-查询")
public class SysMenuQuery implements Serializable {

	@ApiModelProperty(value = "页码", example = "1")
	private int pageNo = 1;

	@ApiModelProperty(value = "条数", example = "10")
	private int pageSize = 10;

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

  	@ApiModelProperty(value = "菜单类型，0父级，1子级，2按钮" )
	private Integer type;

  	@ApiModelProperty(value = "业务系统id,-1系统功能" )
	private String businessSystemId;

  	@ApiModelProperty(value = "图片样式" )
	private String icon;

  	@ApiModelProperty(value = "状态是否可以用,0禁用,1启用" )
	private Integer status;


}
