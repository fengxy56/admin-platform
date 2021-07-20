package com.hongkun.model.vo.apply;

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
public class HxSignTrackRecordSummaryVO implements Serializable {


	@ApiModelProperty(value = "公司名称" )
	private String cityCompanyName;

	@ApiModelProperty(value = "项目名称" )
	private String projectName;

  	@ApiModelProperty(value = "监理公司" )
	private String jlCompanyName;

  	@ApiModelProperty(value = "账号" )
	private String account;

	@ApiModelProperty(value = "姓名" )
	private String name;

	@ApiModelProperty(value = "岗位" )
	private String jobTitle;

	@ApiModelProperty(value = "是否在职" )
	private String status;

	@ApiModelProperty(value = "最后修改时间" )
	private String lastUpdateTime;

  	@ApiModelProperty(value = "打卡天数" )
	private String dkCount;

}
