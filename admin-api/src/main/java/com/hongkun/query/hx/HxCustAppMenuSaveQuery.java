package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName HxCustAppInfoQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:54
 */
@Data
@ApiModel("轻应用菜单保存-请求参数")
public class HxCustAppMenuSaveQuery {

    @NotBlank(message = "轻应用id不能为空")
    @ApiModelProperty(value = "轻应用" )
    private String appId;

    @NotBlank(message = "应用名称不能为空")
    @ApiModelProperty(value = "应用名称" )
    private String appName;

    @NotNull(message = "父级应用appid不能为空")
    @ApiModelProperty(value = "父级应用appid" )
    private String parentAppId;

    @NotBlank(message = "父级轻应用名称不能为空")
    @ApiModelProperty(value = "父级轻应用名称" )
    private String parentAppName;


    @ApiModelProperty(value = "app跳转地址" )
    private String appAddress;

    @ApiModelProperty(value = "跳转地址类型：1外部连接，2内部连接" )
    private Integer appAddressType;


    @ApiModelProperty(value = "app头像地址" )
    private String appPhotoUrl;


    @ApiModelProperty(value = "app头像地址类型" )
    private Integer appPhotoUrlType;

    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序" )
    private Integer showOrder;

    @NotNull(message = "是否有效不能为空")
    @ApiModelProperty(value = "是否有效：0无效，1有效" )
    private Integer status;

    @NotNull(message = "级别不能为空")
    @ApiModelProperty(value = "级别" )
    private Integer level;

    @ApiModelProperty(value = "备注" )
    private String remark;

    @ApiModelProperty(value = "扩展字段1" )
    private String ext1;

    @ApiModelProperty(value = "扩展字段2" )
    private String ext2;

    @ApiModelProperty(value = "扩展字段3" )
    private String ext3;

    @ApiModelProperty(value = "扩展字段4" )
    private String ext4;

    @ApiModelProperty(value = "扩展字段5" )
    private String ext5;

    @ApiModelProperty(value = "操作类型" )
    private String actionType;

}
