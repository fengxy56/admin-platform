package com.hongkun.bean.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 鸿信组织
 *
 * @author fengxiaoyang
 * @date 2020-7-30 7:16:34
 */
@Data
@ApiModel("鸿信组织")
@TableName("hx_org")
public class HxOrg implements Serializable {

    @ApiModelProperty(value = "主键id,组织id")
    private String id;

    @ApiModelProperty(value = "父级id")
    private String parentId;

    @ApiModelProperty(value = "组织名称")
    private String name;

    @ApiModelProperty(value = "组织长名称")
    private String department;

    @ApiModelProperty(value = "是否是商务伙伴部门，是为1，否为0")
    private Integer partnerType;

    @ApiModelProperty(value = "部门是否设为组织，1表示是组织，0表示不是")
    private Integer isOrganization;

    @ApiModelProperty(value = "null")
    private String inChargers;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
