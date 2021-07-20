package com.hongkun.model.vo.hx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-25 8:23:14
 */
@Data
@ApiModel("工作更新统计汇总")
public class UserWorkReportCountVO implements Serializable {

    @ApiModelProperty(value = "访问账户")
    private String userid;

    @ApiModelProperty(value = "访问账户姓名")
    private String name;

    @ApiModelProperty(value = "访问组织")
    private String department;

    @ApiModelProperty(value = "访问岗位")
    private String jobTitle;

    @ApiModelProperty(value = "类型")
    private String type;

    @ApiModelProperty(value = "次数")
    private String num;

}
