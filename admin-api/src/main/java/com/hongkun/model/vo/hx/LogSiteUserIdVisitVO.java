package com.hongkun.model.vo.hx;

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
@ApiModel("用户点击统计汇总")
public class LogSiteUserIdVisitVO implements Serializable {

    @ApiModelProperty(value = "访问账户")
    private String userid;

    @ApiModelProperty(value = "访问账户姓名")
    private String name;

    @ApiModelProperty(value = "访问组织")
    private String department;

    @ApiModelProperty(value = "访问岗位")
    private String jobTitle;

    @ApiModelProperty(value = "访问时间")
    private String visitDate;

    @ApiModelProperty(value = "访问次数")
    private String visitCount;
}
