package com.hongkun.query.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 签到轨迹配置信息表
 *
 * @author fengxiaoyang
 * @date 2020-6-19 11:59:03
 */
@Data
@ApiModel("签到轨迹配置信息查询-请求表")
public class HxSignTrackConfigureQuery implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "别名")
    private String alias;

    @ApiModelProperty(value = "签到名称")
    private String positionName;

}
