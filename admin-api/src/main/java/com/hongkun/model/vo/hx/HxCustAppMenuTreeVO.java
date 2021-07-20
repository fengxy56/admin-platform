package com.hongkun.model.vo.hx;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hongkun.bean.hx.HxCustAppMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: 鸿信轻应用菜单列表
 *
 * @author fengxiaoyang
 * @date 2020-11-30 16:29:50
 */
@Data
@ApiModel("鸿信轻应用菜单列表")
@TableName("hx_cust_app_menu")
public class HxCustAppMenuTreeVO implements Serializable {

  	@ApiModelProperty(value = "轻应用" )
	private String appId;

  	@ApiModelProperty(value = "应用名称" )
	private String appName;

  	@ApiModelProperty(value = "父级应用appid" )
	private String parentAppId;

  	@ApiModelProperty(value = "父级轻应用名称" )
	private String parentAppName;

  	@ApiModelProperty(value = "app跳转地址" )
	private String appAddress;

  	@ApiModelProperty(value = "跳转地址类型：1外部连接，2内部连接" )
	private Integer appAddressType;

  	@ApiModelProperty(value = "app头像地址" )
	private String appPhotoUrl;

  	@ApiModelProperty(value = "排序" )
	private Integer appPhotoUrlType;

  	@ApiModelProperty(value = "排序" )
	private Integer showOrder;

  	@ApiModelProperty(value = "是否有效：0无效，1有效" )
	private Integer status;

	@ApiModelProperty(value = "级别" )
	private Integer level;

  	@ApiModelProperty(value = "备注" )
	private String remark;

  	@ApiModelProperty(value = "扩展字段1" )
	private String ext1;

  	@ApiModelProperty(value = "扩展字段2" )
	private String ext2;

  	@ApiModelProperty(value = "扩展字段3" )
	private String ext3;

  	@ApiModelProperty(value = "扩展字段4" )
	private String ext4;

  	@ApiModelProperty(value = "扩展字段5" )
	private String ext5;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "修改时间" )
	private Date updateTime;

  	@ApiModelProperty(value = "修改人" )
	private String updateAccout;

  	@ApiModelProperty(value = "修改名称" )
	private String updateName;

	@ApiModelProperty(value = "子类")
	private List<HxCustAppMenuTreeVO> children;

}
