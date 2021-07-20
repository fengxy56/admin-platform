package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 字典表
 *
 * @author fengxiaoyang
 * @date 2020-4-18 13:34:27
 */
@Data
@ApiModel("字典表")
@TableName("sys_dict")
public class SysDict implements Serializable {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "字典组")
    private String dictGroup;

    @ApiModelProperty(value = "字典组编码")
    private String dictCode;

    @ApiModelProperty(value = "字典组名称")
    private String dictName;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "扩展字段1")
    private String ext1;

    @ApiModelProperty(value = "扩展字段2")
    private String ext2;

    @ApiModelProperty(value = "扩展字段3")
    private String ext3;

    @ApiModelProperty(value = "扩展字段4")
    private String ext4;

    @ApiModelProperty(value = "扩展字段5")
    private String ext5;

    @ApiModelProperty(value = "扩展字段6")
    private String ext6;

    @ApiModelProperty(value = "扩展字段7")
    private String ext7;

    @ApiModelProperty(value = "扩展字段8")
    private String ext8;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "修改账户")
    private String updateAccount;

    @ApiModelProperty(value = "0无效 1有效")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer showOrder;


}
