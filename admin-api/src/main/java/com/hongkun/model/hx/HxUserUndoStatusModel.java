package com.hongkun.model.hx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName HxUserUndoStatusModel
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:50
 */
@Data
public class HxUserUndoStatusModel {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "账户")
    private String account;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "轻应用id")
    private String appId;


    @ApiModelProperty(value = "消息id")
    private String msgId;

    @ApiModelProperty(value = "待办已办状态")
    private String todoStatus;


    @ApiModelProperty(value = "标题")
    private String content;

    @ApiModelProperty(value = "开始时间")
    private Date createTime;

    @ApiModelProperty(value = "结束时间")
    private Date updateTime;

    @ApiModelProperty(value = "是否我发起")
    private boolean ifAuthor;

    @ApiModelProperty(value = "是否抄送")
    private boolean iscc;

    @ApiModelProperty(value = "是否抄送")
    private boolean delete;

    @ApiModelProperty(value = "发起人")
    private String authorUser;


}
