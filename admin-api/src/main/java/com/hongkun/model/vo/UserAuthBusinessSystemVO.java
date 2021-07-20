package com.hongkun.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName UserBusinessSystemVO
 * @Description 这里描述
 * @Author admin
 * @Date 2021/4/16 8:53
 */
@ApiModel("用户授权系统返回")
@Data
public class UserAuthBusinessSystemVO {

    @ApiModelProperty(value = "主键id，业务系统id" )
    private String id;

    @ApiModelProperty(value = "业务系统名称" )
    private String businessSystemName;

}
