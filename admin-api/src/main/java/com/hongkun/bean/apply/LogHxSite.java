package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 智慧工地点击日志
 *
 * @author fengxiaoyang
 * @date 2020-6-5 19:14:15
 */
@Data
@ApiModel("智慧工地点击日志")
@TableName("log_hx_site")
public class LogHxSite implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "用户账户")
    private String userid;

    @ApiModelProperty(value = "父类组织")
    private String parentIndexCode;

    @ApiModelProperty(value = "子类组织")
    private String regionIndexCode;

    @ApiModelProperty(value = "设备id")
    private String cameraIndexCode;

    @ApiModelProperty(value = "设备名称")
    private String cameraName;

    @ApiModelProperty(value = "客户端类型")
    private String versionType;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "创建时间")
    private String createtime;

    @ApiModelProperty(value = "文本")
    private String text;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "状态")
    private String status;


}
