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
@ApiModel("同事圈修改内容-请求参数")
public class WorkoUpdateQuery {


    @ApiModelProperty(value = "主键id" )
    private Integer id;

    @ApiModelProperty(value = "是否置顶：0否，1是" )
    private Integer isTop;

    @ApiModelProperty(value = "是否删除：0否，1是" )
    private Integer isDelete;


}
