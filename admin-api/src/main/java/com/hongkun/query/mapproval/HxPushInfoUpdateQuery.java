package com.hongkun.query.mapproval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @ClassName HxUserUndoStatusQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:42
 */
@Data
@ApiModel("鸿信待办-请求参数")
public class HxPushInfoUpdateQuery {

    @NotNull(message = "主键id不能为空")
    @ApiModelProperty(value = "主键id")
    private Integer id;

    @NotBlank(message = "鸿信消息id不能为空")
    @ApiModelProperty(value = "鸿信消息id")
    private String hxMsgId;

    @NotBlank(message = "账号不能为空")
    @ApiModelProperty(value = "账号")
    private String userId;

    @ApiModelProperty(value = "是否删除,0否，1是")
    private Integer deleteStatus;

    @NotNull(message = "待办已办状态不能为空")
    @ApiModelProperty(value = "0已办,1待办")
    private Integer ifTodo;


}
