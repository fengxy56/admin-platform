package com.hongkun.model.hx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName HxWifiInfoModel
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 15:15
 */
@Data
public class HxWifiInfoModel {

    @ApiModelProperty(value = "wifi名称")
    private String ssid;

    @ApiModelProperty(value = "ip范围")
    private String bssid;

    @ApiModelProperty(value = "地址备注")
    private String infoAddress;

    @ApiModelProperty(value = "签到点经度")
    private Integer type = 0;

    @ApiModelProperty(value = "签到点经度")
    private boolean hasNotify = false;

}
