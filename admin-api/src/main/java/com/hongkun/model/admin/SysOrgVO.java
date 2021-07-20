package com.hongkun.model.admin;

import com.hongkun.bean.SysOrg;
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
public class SysOrgVO  extends SysOrg implements Serializable{


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

    List<SysOrgVO> children;

}
