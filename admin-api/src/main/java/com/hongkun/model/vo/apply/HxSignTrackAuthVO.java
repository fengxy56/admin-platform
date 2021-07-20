package com.hongkun.model.vo.apply;

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
@ApiModel("签到轨迹用户角色授权列表菜单返回")
public class HxSignTrackAuthVO implements Serializable {


    @ApiModelProperty(value = "权限类型：0管理员，1账户、2角色")
    private Integer authType;

    @ApiModelProperty(value = "权限类型：1账户、2角色")
    private String authTypeStr;

    @ApiModelProperty(value = "权限ID")
    private String authId;

    @ApiModelProperty(value = "权限名称")
    private String authName;

    @ApiModelProperty(value = "轻应用appid")
    private String configureId;

    @ApiModelProperty(value = "轻应用名称")
    private String positionName;


}
