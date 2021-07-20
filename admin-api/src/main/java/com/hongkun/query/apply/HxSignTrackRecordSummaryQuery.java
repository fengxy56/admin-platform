package com.hongkun.query.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹配置信息表
 *
 * @author fengxiaoyang
 * @date 2020-6-19 11:59:03
 */
@Data
@ApiModel("签到轨迹记录汇总查询-请求表")
public class HxSignTrackRecordSummaryQuery implements Serializable {


    @ApiModelProperty(value = "城市公司")
    private String cityCompanyName;

    @ApiModelProperty(value = "项目")
    private String projectName;

    @ApiModelProperty(value = "监理公司")
    private String jlCompanyName;

    @ApiModelProperty(value = "开始签到时间")
    private String startSignTime;

    @ApiModelProperty(value = "结束签到时间")
    private String endSignTime;


}
