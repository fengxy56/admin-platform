package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹配置信息表
 *
 * @author fengxiaoyang
 * @date 2020-7-16 18:11:58
 */
@Data
@ApiModel("签到轨迹配置信息表")
@TableName("hx_sign_track_configure")
public class HxSignTrackConfigure implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "签到名称")
    private String positionName;

    @ApiModelProperty(value = "经度")
    private double longitude;

    @ApiModelProperty(value = "纬度")
    private double latitude;

    @ApiModelProperty(value = "允许的便宜量")
    private Integer offset;

    @ApiModelProperty(value = "公司")
    private String companyName;

    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "创建人")
    private String createUserName;

    @ApiModelProperty(value = "修改人")
    private String updateUserName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
