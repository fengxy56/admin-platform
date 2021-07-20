package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 系统信息
 *
 * @author fengxiaoyang
 * @date 2020-4-18 11:56:36
 */
@Data
@ApiModel("系统信息")
@TableName("sys_info")
public class SysInfo implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "系统code")
    private String sysCode;

    @ApiModelProperty(value = "系统名称")
    private String sysName;

    @ApiModelProperty(value = "系统认证秘钥")
    private String sysSercret;

    @ApiModelProperty(value = "认证token")
    private String token;

    @ApiModelProperty(value = "状态,0禁用,1启用")
    private Integer status;

    @ApiModelProperty(value = "修改用户")
    private String updateAccount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
