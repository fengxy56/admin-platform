package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 鸿信轮播图
 *
 * @author fengxiaoyang
 * @date 2020-9-23 16:01:45
 */
@Data
@ApiModel("鸿信轮播图")
@TableName("hx_poll_image")
public class HxPollImage implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private Integer id;

  	@ApiModelProperty(value = "图片地址" )
	private String pollImage;

  	@ApiModelProperty(value = "图片跳转路径" )
	private String imageUrl;

  	@ApiModelProperty(value = "标题" )
	private String urlTitle;

  	@ApiModelProperty(value = "类型，1首页轮播图" )
	private Integer type;

  	@ApiModelProperty(value = "排序" )
	private Integer sort;


}
