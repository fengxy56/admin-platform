package com.hongkun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName TBdPersonQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/31 16:19
 */
@Data
@ApiModel("鸿信人员信息-请求参数")
public class TBdPersonQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;


    @ApiModelProperty(value = "用户userId",example = "a2037aa0-a67a-11e8-ba33-0050569fa06e")
    private String userId;

    @ApiModelProperty(value = "用户oid")
    private String oid;

    @ApiModelProperty(value = "用户openId")
    private String openId;

    @ApiModelProperty(value = "账户")
    private String phone;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "状态",example = "1")
    private Integer status;

    @ApiModelProperty(value = "最后修改实际")
    private String lastUpdateTime;

    @ApiModelProperty(value = "企业eid",example = "101")
    private String eid;

}