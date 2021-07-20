package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName TBdPersonQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 8:11
 */
@Data
@ApiModel("鸿信人员信息-请求参数")
public class TBdPersonQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "账户")
    private String phone;

    @ApiModelProperty(value = "项目")
    private String name;


    @ApiModelProperty(value = "状态")
    private Integer status;


}
