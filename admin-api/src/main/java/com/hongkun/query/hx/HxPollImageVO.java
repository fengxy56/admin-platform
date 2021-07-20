package com.hongkun.query.hx;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

import java.io.Serializable;

/**
 * Description: null
 *
 * @author poplar
 * @date 2020年4月15日 下午2:25:30
 */
@Data
@ApiModel("轮播图实体类 - 返回实体类")
public class HxPollImageVO implements Serializable {

    @ApiModelProperty(value = "主键id")
    private Integer id;

    @ApiModelProperty(value = "原图片路径")
    private String imageUrlPath;

    @ApiModelProperty(value = "点击跳转地址")
    private String goToUrl;

    @ApiModelProperty(value = "排序")
    private Integer showOrder;

    @ApiModelProperty(value = "显示名称")
    private String showDescribe;

    @ApiModelProperty(value = "显示名称")
    private Integer type;

}
