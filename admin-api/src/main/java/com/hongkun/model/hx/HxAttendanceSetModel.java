package com.hongkun.model.hx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @ClassName HxAttendanceSetModel
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 14:05
 */
@Data
public class HxAttendanceSetModel {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "签到点名称")
    private String positionName;

    @ApiModelProperty(value = "签到点地址")
    private String address;

    @ApiModelProperty(value = "签到点范围")
    private String offset;

    @ApiModelProperty(value = "签到点经度")
    private String lng;

    @ApiModelProperty(value = "签到点纬度")
    private String lat;

    @ApiModelProperty(value = "签到点纬度")
    private Date updateTime;

    @ApiModelProperty(value = "wifi信息集合")
    private List<HxWifiInfoModel> wifiInoList;

}
