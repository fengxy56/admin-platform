package com.hongkun.query.hx.mongodb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName HxUserUndoStatusQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:42
 */
@Data
@ApiModel("鸿信签到点-请求参数")
public class HxAttendanceSetQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "主键")
    private String id;

    @ApiModelProperty(value = "签到点名称")
    private String positionName;

    @ApiModelProperty(value = "签到点地址")
    private String address;


}
