package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @ClassName TBdPersonQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 8:11
 */
@Data
@ApiModel("鸿信修改用户最后时间-请求参数")
public class TBdPersonUpdateQuery {

    @NotNull(message = "用户oid不能为空")
    @ApiModelProperty(value = "用户oid")
    private String oid;

    @NotNull(message = "账户不能为空")
    @ApiModelProperty(value = "账户")
    private String phone;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty(value = "状态")
    private Integer status;

}
