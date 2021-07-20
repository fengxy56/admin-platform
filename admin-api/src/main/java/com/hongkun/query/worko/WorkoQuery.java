package com.hongkun.query.worko;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @ClassName WorkoQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 15:45
 */
@Data
@ApiModel("同事圈内容-请求参数")
public class WorkoQuery {

    @ApiModelProperty(value = "页码", example = "1")
    private int pageNo = 1;

    @ApiModelProperty(value = "条数", example = "10")
    private int pageSize = 10;

    @ApiModelProperty(value = "是否删除：0否，1是" )
    private Integer isDelete;

    @ApiModelProperty(value = "点问类型：-1否，1是" )
    private Integer dwType;

    @ApiModelProperty(value = "发布人账号" )
    private String pubUserName;

    @ApiModelProperty(value = "发布人姓名" )
    private String pubUserTrueName;

    @ApiModelProperty(value = "发布内容" )
    private String pubWordsContent;

}
