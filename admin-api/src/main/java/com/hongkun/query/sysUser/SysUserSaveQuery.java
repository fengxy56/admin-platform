package com.hongkun.query.sysUser;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @ClassName SysUserQuery
 * @Description 这里描述
 * @Author sys
 * @Date 2021/1/16 14:16
 */
@Data
@ApiModel("系统用户-请求参数")
public class SysUserSaveQuery {

    @NotBlank(message = "账户不能为空")
    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "确认密码")
    private String surePassword;

    @NotBlank(message = "姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @NotBlank(message = "手机号不能为空")
    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "岗位")
    private String jobTitle;

    @ApiModelProperty(value = "性别：0未知，1男，2女")
    private Integer gender;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "组织id")
    private String orgId;

    @ApiModelProperty(value = "组织名称")
    private String orgName;

    @ApiModelProperty(value = "组织长名称")
    private String orgLongName;

    @ApiModelProperty(value = "是否禁用：0否，1是")
    private Integer status;

}
