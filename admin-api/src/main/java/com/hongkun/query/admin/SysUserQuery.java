package com.hongkun.query.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName SysUserQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2021/1/16 14:16
 */
@Data
@ApiModel("系统用户-请求参数")
public class SysUserQuery {


    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;


    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "组织id")
    private String orgId;


    @ApiModelProperty(value = "是否禁用：0否，1是")
    private Integer status;

}
