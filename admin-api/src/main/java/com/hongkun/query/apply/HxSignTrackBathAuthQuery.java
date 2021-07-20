package com.hongkun.query.apply;

import com.hongkun.model.excel.hx.HxAppAuthExcel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel("用户批量授权签到轨迹-请求参数")
public class HxSignTrackBathAuthQuery {

    @ApiModelProperty(value = "菜单id")
    private List<HxAppAuthExcel> list;

    @ApiModelProperty(value = "配置id", example = "1")
    private String configureId;

}
