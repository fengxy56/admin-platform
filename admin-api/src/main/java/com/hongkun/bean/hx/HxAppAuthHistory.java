package com.hongkun.bean.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 轻应用授权
 *
 * @author fengxiaoyang
 * @date 2020-5-29 9:19:48
 */
@Data
@ApiModel("轻应用授权")
@TableName("hx_app_auth_history")
public class HxAppAuthHistory implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String uuid;

    @ApiModelProperty(value = "hx_app_auth表主键id")
    private String id;

    @ApiModelProperty(value = "轻应用appid")
    private Integer appId;

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
