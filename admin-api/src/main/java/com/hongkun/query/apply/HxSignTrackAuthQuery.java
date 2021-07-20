package com.hongkun.query.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-25 8:23:14
 */
@Data
@ApiModel("app用户角色授权列表菜单返回")
public class HxSignTrackAuthQuery implements Serializable {


    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "权限类型：1账户、2角色")
    private Integer authType;

    @ApiModelProperty(value = "轻应用appid")
    private String configureId;

    @ApiModelProperty(value = "轻应用名称")
    private String positionName;

    @ApiModelProperty(value = "权限ID")
    private String authId;

    @ApiModelProperty(value = "权限名称")
    private String authName;

    @ApiModelProperty(value = "鸿信企业组织id")
    private String orgInfoId;

}
