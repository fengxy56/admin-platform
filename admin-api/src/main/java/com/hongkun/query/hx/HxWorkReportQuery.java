package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName LogHxSiteQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/5 19:43
 */
@Data
@ApiModel("工作更新-查询")
public class HxWorkReportQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "用户账户")
    private String userid;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "组织")
    private String department;

    @ApiModelProperty(value = "标题")
    private String jobTitle;

    @ApiModelProperty(value = "访问时间")
    private String createTime;


}
