package com.hongkun.bean.hx;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 鸿信轻应用新增表
 *
 * @author fengxiaoyang
 * @date 2020-5-28 18:48:29
 */
@Data
@ApiModel("鸿信轻应用新增表")
@TableName("hx_cust_app_info")
public class HxCustAppInfo implements Serializable {

    @ApiModelProperty(value = "轻应用")
    @TableId
    private Integer appId;

    @ApiModelProperty(value = "null")
    private Integer parentAppId;

    @ApiModelProperty(value = "应用名称")
    private String appName;

    @ApiModelProperty(value = "应用类型1轻应用8原生应用")
    private Integer appType;

    @ApiModelProperty(value = "排序")
    private Integer showOrder;

    @ApiModelProperty(value = "是否置顶")
    private Integer isTop;

    @ApiModelProperty(value = "0禁用，1全员,2部分人员")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改人")
    private String updateAccout;


}
