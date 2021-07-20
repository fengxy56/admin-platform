package com.hongkun.query.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹配置信息表
 *
 * @author fengxiaoyang
 * @date 2020-6-19 11:59:03
 */
@Data
@ApiModel("签到轨迹配置信息-请求表")
@TableName("hx_sign_track_configure")
public class HxSignTrackConfigureSaveQuery implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @NotBlank(message = "地址不能为空")
    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "别名")
    private String alias;

    @NotBlank(message = "签到名称不能为空")
    @ApiModelProperty(value = "签到名称")
    private String positionName;

    @NotNull(message = "经度不能为空")
    @ApiModelProperty(value = "经度")
    private double longitude;

    @NotNull(message = "纬度不能为空")
    @ApiModelProperty(value = "纬度")
    private double latitude;

    @NotNull(message = "签到范围不能为空")
    @ApiModelProperty(value = "签到范围:允许的偏移量")
    private Integer offset;

    @NotBlank(message = "公司不能为空")
    @ApiModelProperty(value = "公司")
    private String companyName;

    @NotBlank(message = "项目名称不能为空")
    @ApiModelProperty(value = "项目名称")
    private String projectName;

    @ApiModelProperty(value = "创建人")
    private String createUserName;

    @ApiModelProperty(value = "修改人")
    private Date updateUserName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
