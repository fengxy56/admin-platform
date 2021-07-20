package com.hongkun.query.apply;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: 鸿信二维码信息
 *
 * @author fengxiaoyang
 * @date 2020-7-27 14:29:34
 */
@Data
@ApiModel("鸿信二维码信息-请求参数")
public class HxCodeInfoQuery implements Serializable {

    @ApiModelProperty(value = "页码", example = "1")
    private Integer pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private Integer pageSize = 10;


    @ApiModelProperty(value = "null")
    private Integer id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "跳转访问url")
    private String goUrl;

    @ApiModelProperty(value = "下载地址")
    private String downUrl;

    @ApiModelProperty(value = "logo地址")
    private String logo;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;


}
