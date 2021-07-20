package com.hongkun.query.hx.mongodb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName HxUserUndoStatusQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:42
 */
@Data
@ApiModel("鸿信待办-请求参数")
public class HxUserUndoStatusQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;

    @ApiModelProperty(value = "轻应用id")
    private String appId;

    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "标题")
    private String content;

    @ApiModelProperty(value = "待办已办状态")
    private String todoStatus;

    @ApiModelProperty(value = "是否删除")
    private String deleteStatus;


    @ApiModelProperty(value = "开始时间")
    private Date startDate;

    @ApiModelProperty(value = "结束时间")
    private Date endDate;


}
