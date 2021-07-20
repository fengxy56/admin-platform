package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户角色表
 *
 * @author fengxiaoyang
 * @date 2020-7-29 17:40:32
 */
@Data
@ApiModel("用户角色表")
@TableName("uv_erole_user_view")
public class UvEroleUserView implements Serializable {

    @ApiModelProperty(value = "用户账号")
    private String accountName;

    @ApiModelProperty(value = "标准角色id")
    private String bRoleId;

    @ApiModelProperty(value = "标准角色名称")
    private String bRoleName;

    @ApiModelProperty(value = "用户名称")
    private String chsName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "实例角色Id")
    private String eRoleId;

    @ApiModelProperty(value = "实例角色名称")
    private String eRoleName;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "备注")
    private String remarks;

    @ApiModelProperty(value = "状态:0为正常，1为禁用 ")
    private Integer statusType;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "用户ID")
    private String userId;

    @ApiModelProperty(value = "鸿信创建时间")
    private Date hxCreateTime;

    @ApiModelProperty(value = "鸿信更新时间")
    private Date hxUpdateTime;


}
