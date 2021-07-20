package com.hongkun.bean.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("置业顾问信息")
@Data
public class UvUser {

    @ApiModelProperty(value = "内部账号")
    private String mobilePhone;
}
