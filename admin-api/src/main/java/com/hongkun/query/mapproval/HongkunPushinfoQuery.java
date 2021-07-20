package com.hongkun.query.mapproval;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 签到轨迹配置信息表
 *
 * @author fengxiaoyang
 * @date 2020-6-19 11:59:03
 */
@Data
@ApiModel("签到轨迹配置信息查询-请求表")
public class HongkunPushinfoQuery implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "应用id（防止调用错误）")
    private String appId;

    @ApiModelProperty(value = "业务消息id")
    private String requestId;

    @ApiModelProperty(value = "当前操作用户ID")
    private String userId;

    @ApiModelProperty(value = "审批标题")
    private String msgTitle;

    @ApiModelProperty(value = "1待办，0已办")
    private String ifTodo;

    @ApiModelProperty(value = "鸿信消息id")
    private String hxMsgId;


}
