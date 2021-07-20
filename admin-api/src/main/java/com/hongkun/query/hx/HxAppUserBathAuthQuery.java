package com.hongkun.query.hx;

import com.hongkun.model.excel.hx.HxAppAuthExcel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户批量授权请求参数")
public class HxAppUserBathAuthQuery {

    @ApiModelProperty(value = "菜单id")
    private List<HxAppAuthExcel> list;

    @ApiModelProperty(value = "轻应用appid", example = "1")
    private Integer appId;

}
