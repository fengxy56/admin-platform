package com.hongkun.model.vo.hx;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Description: 智慧工地点击日志
 *
 * @author fengxiaoyang
 * @date 2020-6-5 19:14:15
 */
@Data
@ApiModel("智慧工地点击日志")
@TableName("log_hx_site")
public class LogHxSiteVO implements Serializable {


    @ApiModelProperty(value = "用户账户")
    private String userid;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "组织")
    private String department;

    @ApiModelProperty(value = "标题")
    private String jobTitle;

    @ApiModelProperty(value = "访问时间")
    private String createTime;


}
