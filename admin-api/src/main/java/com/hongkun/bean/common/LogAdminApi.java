package com.hongkun.bean.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-5-29 10:17:57
 */
@Data
@ApiModel("日志系统拦截记录")
@TableName("log_admin_api")
public class LogAdminApi implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "访问人id")
    private String userId;

    @ApiModelProperty(value = "访问人姓名")
    private String userName;

    @ApiModelProperty(value = "地址")
    private String logPathUrl;

    @ApiModelProperty(value = "动作")
    private String logAction;

    @ApiModelProperty(value = "备注")
    private String logRemark;

    @ApiModelProperty(value = "方法")
    private String logMethod;

    @ApiModelProperty(value = "null")
    private String headParam;

    @ApiModelProperty(value = "null")
    private String logMethodName;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "日志ip")
    private String logIp;

    @ApiModelProperty(value = "接口系统类型")
    private String apiSysType;


}
