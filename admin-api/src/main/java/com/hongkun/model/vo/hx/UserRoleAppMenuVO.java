package com.hongkun.model.vo.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-25 8:23:14
 */
@Data
@ApiModel("用户角色app菜单列表返回")
public class UserRoleAppMenuVO implements Serializable {

    @ApiModelProperty(value = "轻应用id")
    private String menuId;

    @ApiModelProperty(value = "轻应用名称")
    private String menuName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "0菜单、1按钮")
    private Integer type;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "是否展开")
    private boolean expand = true;

    @ApiModelProperty(value = "角色菜单权限,false没有权限，true有权限")
    private Boolean checked;

    @ApiModelProperty(value = "子菜单")
    private List<UserRoleAppMenuVO> children;


}
