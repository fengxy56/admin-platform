package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 短信系统配置
 *
 * @author fengxiaoyang
 * @date 2020-7-16 19:33:51
 */
@Data
@ApiModel("短信系统配置")
@TableName("sms_sys_info")
public class SmsSysInfo implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "应用appid")
    private String smsKey;

    @ApiModelProperty(value = "应用密钥")
    private String smsSecret;

    @ApiModelProperty(value = "系统名称")
    private String sysName;

    @ApiModelProperty(value = "短信是否开启,0启动，1禁用")
    private Integer smsPush;

    @ApiModelProperty(value = "短信字典id")
    private String smsDictId;

    @ApiModelProperty(value = "鸿信通知是否开启,0启动，1禁用")
    private Integer hxPush;

    @ApiModelProperty(value = "鸿信通知字典id")
    private String hxNotifyAppDictId;

    @ApiModelProperty(value = "0禁用,1启用")
    private Integer status;

    @ApiModelProperty(value = "修改人")
    private String updateAccount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
