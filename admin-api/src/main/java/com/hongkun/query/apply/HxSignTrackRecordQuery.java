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
@ApiModel("签到轨迹记录查询-请求表")
public class HxSignTrackRecordQuery implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "公司")
    private String companyName;

    @ApiModelProperty(value = "项目")
    private String projectName;


    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "签到名称")
    private String positionName;

    @ApiModelProperty(value = "开始签到时间")
    private String startSignTime;

    @ApiModelProperty(value = "结束签到时间")
    private String endSignTime;


}
