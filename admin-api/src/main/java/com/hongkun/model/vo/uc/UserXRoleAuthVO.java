package com.hongkun.model.vo.uc;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-25 8:23:14
 */
@Data
@ApiModel("菜单按钮返回")
public class UserXRoleAuthVO implements Serializable {

    @ApiModelProperty(value = "菜单id")
    private String menuId;

    @ApiModelProperty(value = "名称")
    private String menuName;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "编码")
    private String menuCode;

    @ApiModelProperty(value = "0菜单、1按钮")
    private Integer type;

    @ApiModelProperty(value = "上级ID")
    private String parentId;

    @ApiModelProperty(value = "url")
    private String menuUrl;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "0无效 1有效")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer showOrder;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "所属系统ID")
    private String systemId;

    @ApiModelProperty(value = "是否展开")
    private boolean expand = true;

    @ApiModelProperty(value = "角色菜单权限,false没有权限，true有权限")
    private Boolean checked;

    @ApiModelProperty(value = "版本id")
    private String versionId;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "子菜单")
    private List<UserXRoleAuthVO> children;


}
