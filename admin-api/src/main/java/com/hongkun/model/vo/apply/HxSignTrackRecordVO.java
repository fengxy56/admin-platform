package com.hongkun.model.vo.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("签到轨迹记录-返回")
public class HxSignTrackRecordVO implements Serializable {


	@ApiModelProperty(value = "公司名称" )
	private String companyName;

	@ApiModelProperty(value = "项目名称" )
	private String projectName;

  	@ApiModelProperty(value = "主键id" )
	private String recordId;

  	@ApiModelProperty(value = "姓名" )
	private String userName;

  	@ApiModelProperty(value = "账号" )
	private String account;

	@ApiModelProperty(value = "岗位" )
	private String jobTitle;

	@ApiModelProperty(value = "是否在职" )
	private String status;

	@ApiModelProperty(value = "最后修改时间" )
	private String lastUpdateTime;

  	@ApiModelProperty(value = "签到id" )
	private String positionId;

  	@ApiModelProperty(value = "签到名称" )
	private String positionName;

  	@ApiModelProperty(value = "签到时间" )
	private Date signTime;




}
