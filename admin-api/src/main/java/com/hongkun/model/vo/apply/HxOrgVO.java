package com.hongkun.model.vo.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: 鸿信组织
 *
 * @author fengxiaoyang
 * @date 2020-7-30 7:16:34
 */
@Data
@ApiModel("鸿信组织-返回数据")
public class HxOrgVO implements Serializable {

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

    @ApiModelProperty(value = "0菜单、1按钮")
    private Integer type;

    @ApiModelProperty(value = "菜单id")
    private String menuId;

    @ApiModelProperty(value = "名称")
    private String menuName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "是否展开")
    private boolean expand = false;

    @ApiModelProperty(value = "角色菜单权限,false没有权限，true有权限")
    private Boolean checked;


    List<HxOrgVO> children;

}
