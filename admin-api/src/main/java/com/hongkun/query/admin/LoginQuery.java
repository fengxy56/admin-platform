package com.hongkun.query.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @ClassName LoginQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/15 9:47
 */
@Data
@ApiModel("登录-请求参数")
public class LoginQuery {

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号", example = "1001")
    private String account;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码", example = "bpm")
    private String password;


}
