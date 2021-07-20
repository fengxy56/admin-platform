package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 鸿信人员信息表
 *
 * @author fengxiaoyang
 * @date 2020-7-30 7:16:41
 */
@Data
@ApiModel("鸿信人员信息表")
@TableName("hx_person")
public class HxPerson implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "手机号码(账户)")
    private String phone;

    @ApiModelProperty(value = "人员的oid")
    private String oid;

    @ApiModelProperty(value = "人员的openid")
    private String openId;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "头像URL")
    private String photoUrl;

    @ApiModelProperty(value = "是否在通讯录中隐藏手机号码,0: 不隐藏; 1: 隐藏,默认为0")
    private String isHidePhone;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "组织长名称")
    private String department;

    @ApiModelProperty(value = "组织ID")
    private String orgId;

    @ApiModelProperty(value = "职位")
    private String jobTitle;

    @ApiModelProperty(value = "性别,0: 不确定; 1: 男; 2: 女")
    private String gender;

    @ApiModelProperty(value = "状态 0: 注销，1: 正常，2: 禁用")
    private String status;

    @ApiModelProperty(value = "权重（排序）")
    private double weights;

    @ApiModelProperty(value = "1: 负责人， 0： 不是负责人")
    private String orgUserType;

    @ApiModelProperty(value = "办公室电话1")
    private String officePhone1;

    @ApiModelProperty(value = "办公室电话2")
    private String officePhone2;

    @ApiModelProperty(value = "是否是商务伙伴，此接口固定返回0，表示非商务伙伴")
    private String partnerType;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
