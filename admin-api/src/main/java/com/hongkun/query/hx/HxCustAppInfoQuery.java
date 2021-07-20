package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName HxCustAppInfoQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:54
 */
@Data
@ApiModel("轻应用信息-请求参数")
public class HxCustAppInfoQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "轻应用id")
    private Integer appId;

    @ApiModelProperty(value = "轻应用名称")
    private String appName;

    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;

    @ApiModelProperty(value = "应用权限：0禁用，1全员,2部分人员")
    private Integer status;

    @ApiModelProperty(value = "应用类型")
    private Integer appType;

}
