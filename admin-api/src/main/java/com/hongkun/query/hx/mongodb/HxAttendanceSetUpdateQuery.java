package com.hongkun.query.hx.mongodb;

import com.alibaba.fastjson.JSONArray;
import com.hongkun.model.hx.HxWifiInfoModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxAttendanceSetModel
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 14:05
 */
@Data
public class HxAttendanceSetUpdateQuery {

    @NotBlank(message = "主键id不能为空")
    @ApiModelProperty(value = "主键id")
    private String id;

    @NotNull(message = "wifi信息不能为空")
    @ApiModelProperty(value = "wifi信息集合")
    private List<HxWifiInfoModel> wifiInoList;

//    @NotNull(message = "wifi信息不能为空")
//    @ApiModelProperty(value = "wifi信息集合" )
//    private JSONArray wifiInoList;

}
