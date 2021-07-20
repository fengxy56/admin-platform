package com.hongkun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("字典查询请求参数")
public class SysDictQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "字典组", example = "10")
    private String dictGroup;

    @ApiModelProperty(value = "名称", example = "10")
    private String dictName;

    @ApiModelProperty(value = "0无效 1有效", example = "1")
    private Integer status;


}
