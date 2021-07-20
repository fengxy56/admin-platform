package com.hongkun.query.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @ClassName HxCustAppInfoQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:54
 */
@Data
@ApiModel("轻应用信息-请求参数")
public class HxCustAppInfoSaveOrUpdateQuery {

    @NotNull(message = "轻应用id不能为空")
    @ApiModelProperty(value = "轻应用id")
    private Integer appId;

    @NotBlank(message = "轻应用名称不能为空")
    @ApiModelProperty(value = "轻应用名称")
    private String appName;

    @NotNull(message = "应用类型不能为空")
    @ApiModelProperty(value = "应用类型，1轻应用，2固定")
    private Integer appType;

    @NotNull(message = "权限类型不能为空")
    @ApiModelProperty(value = "权限类型0禁用，1全部，2部分")
    private Integer status;

    @ApiModelProperty(value = "轻应用父级parentAppId")
    private Integer parentAppId;


    @NotNull(message = "排序不能为空")
    @ApiModelProperty(value = "排序")
    private Integer showOrder;

    @NotNull(message = "是否置顶不能为空")
    @ApiModelProperty(value = "是否置顶，0否，1置顶")
    private Integer isTop;

    @NotNull(message = "备注不能为空")
    @ApiModelProperty(value = "备注")
    private String remark;


}
