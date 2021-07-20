package com.hongkun.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("字典信息保存或者修改请求参数")
public class SysDictSaveQuery {

    @ApiModelProperty(value = "主键id")
    private String id;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "编码")
    private String dictCode;

    @ApiModelProperty(value = "组")
    private String dictGroup;

    @ApiModelProperty(value = "名称")
    private String dictName;

    @ApiModelProperty(value = "级别")
    private Integer level;

    @ApiModelProperty(value = "备注")
    private String remark;

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

    @ApiModelProperty(value = "排序")
    private Integer showOrder;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "0无效 1有效")
    private Integer status;


}
