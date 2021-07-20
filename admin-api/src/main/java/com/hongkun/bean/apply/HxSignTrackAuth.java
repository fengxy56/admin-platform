package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹授权
 *
 * @author fengxiaoyang
 * @date 2020-7-16 18:11:31
 */
@Data
@ApiModel("签到轨迹授权")
@TableName("hx_sign_track_auth")
public class HxSignTrackAuth implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "配置id")
    private String configureId;

    @ApiModelProperty(value = "权限id")
    private String authId;

    @ApiModelProperty(value = "权限类型,1用户,2角色")
    private Integer authType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人员")
    private String updateAccount;


}
