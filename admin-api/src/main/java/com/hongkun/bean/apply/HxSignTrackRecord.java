package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹记录
 *
 * @author fengxiaoyang
 * @date 2020-11-2 9:20:04
 */
@Data
@ApiModel("签到轨迹记录")
@TableName("hx_sign_track_record")
public class HxSignTrackRecord implements Serializable {

  	@ApiModelProperty(value = "主键id" )
	private String id;

  	@ApiModelProperty(value = "账号" )
	private String userName;

  	@ApiModelProperty(value = "名称" )
	private String account;

  	@ApiModelProperty(value = "null" )
	private Double longitude;

  	@ApiModelProperty(value = "null" )
	private Double latitude;

  	@ApiModelProperty(value = "签到id" )
	private String positionId;

  	@ApiModelProperty(value = "签到名称" )
	private String positionName;

  	@ApiModelProperty(value = "签到定位地址" )
	private String locationAddress;

  	@ApiModelProperty(value = "签到时间" )
	private Date signTime;

  	@ApiModelProperty(value = "创建时间" )
	private Date createTime;

  	@ApiModelProperty(value = "修改时间" )
	private Date updateTime;


}
