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
 * Description: 鸿信二维码信息
 *
 * @author fengxiaoyang
 * @date 2020-7-27 14:29:34
 */
@Data
@ApiModel("鸿信二维码信息")
@TableName("hx_code_info")
public class HxCodeInfo implements Serializable {

    @ApiModelProperty(value = "主键id")
    @TableId(type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "跳转访问url")
    private String goUrl;

    @ApiModelProperty(value = "下载地址")
    private String downUrl;

    @ApiModelProperty(value = "logo地址")
    private String logo;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
