package com.hongkun.bean.apply;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 用户菜单
 *
 * @author fengxiaoyang
 * @date 2020-7-29 18:20:34
 */
@Data
@ApiModel("用户菜单")
@TableName("sys_auth_menu_history")
public class SysAuthMenuHistory implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer uuid;

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "权限id")
    private String authId;

    @ApiModelProperty(value = "权限类型")
    private String authType;

    @ApiModelProperty(value = "工作id")
    private String workId;

    @ApiModelProperty(value = "系统类型")
    private String sysType;

    @ApiModelProperty(value = "系统权限名称")
    private String sysName;

    @ApiModelProperty(value = "菜单")
    private String menuId;

    @ApiModelProperty(value = "修改账户")
    private String updateAccount;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
