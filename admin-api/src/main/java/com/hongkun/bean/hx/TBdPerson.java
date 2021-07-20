package com.hongkun.bean.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 职员
 *
 * @author fengxiaoyang
 * @date 2020-5-26 21:33:31
 */
@Data
@ApiModel("人员信息")
@TableName("t_bd_person")
public class TBdPerson implements Serializable {

    @ApiModelProperty(value = "ID")
    private String id;

    @ApiModelProperty(value = "性别")
    private Integer gender;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "头像URL")
    private String photoUrl;

    @ApiModelProperty(value = "手机")
    private String phone;

    @ApiModelProperty(value = "业务导入电话号码，使用逗号分隔")
    private String phones;

    @ApiModelProperty(value = "电邮")
    private String emails;

    @ApiModelProperty(value = "部门ID")
    private String orgId;

    @ApiModelProperty(value = "部门名称")
    private String department;

    @ApiModelProperty(value = "职位")
    private String jobTitle;

    @ApiModelProperty(value = "userId")
    private String oId;

    @ApiModelProperty(value = "OPENID")
    private String openId;

    @ApiModelProperty(value = "OPEN组织ID")
    private String orgInfoId;

    @ApiModelProperty(value = "null")
    private Integer weights;

    @ApiModelProperty(value = "0：离职，1：正常，2：禁用")
    private Integer status;

    @ApiModelProperty(value = "null")
    private Integer isHidePhone;

    @ApiModelProperty(value = "名称首字符拼音简码")
    private String firstPinyinCode;

    @ApiModelProperty(value = "名称首字母拼音")
    private String firstPinyin;

    @ApiModelProperty(value = "名称全拼")
    private String fullPinyin;

    @ApiModelProperty(value = "名称全拼简码")
    private String fullPinyinCode;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "最后更新时间")
    private Date lastUpdateTime;

    @ApiModelProperty(value = "默认组织")
    private String defaultOrgId;

    @ApiModelProperty(value = "业务ID")
    private String bizId;

    @ApiModelProperty(value = "登录名")
    private String loginName;

    @ApiModelProperty(value = "null")
    private Integer flag;

    @ApiModelProperty(value = "组织编码长名称")
    private String orgLongName;

    @ApiModelProperty(value = "null")
    private String email;

    @ApiModelProperty(value = "null")
    private String wbUserId;

    @ApiModelProperty(value = "是否来自云之家")
    private String isFromWb;

    @ApiModelProperty(value = "null")
    private String importType;

    @ApiModelProperty(value = "邀请人openid")
    private String inviteOpenId;

    @ApiModelProperty(value = "办公电话1")
    private String officePhone1;

    @ApiModelProperty(value = "用户类型：管理员：10，普通用户：20")
    private Integer userType;

    @ApiModelProperty(value = "办公电话2")
    private String officePhone2;

    @ApiModelProperty(value = "名称全拼,没有空格")
    private String fullPinyinNoSpace;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "组织用户类型：0表示普通用户，1表示部门负责人")
    private Integer orgUserType;

    @ApiModelProperty(value = "当前主要用来存储联系方式")
    private String contact;

    @ApiModelProperty(value = "激活时间")
    private Date activeTime;

    @ApiModelProperty(value = "是否外部伙伴")
    private Integer isPartner;

    @ApiModelProperty(value = "微信社区账号token")
    private String token;

    @ApiModelProperty(value = "微信社区账号tokenSecret")
    private String tokenSecret;


}
