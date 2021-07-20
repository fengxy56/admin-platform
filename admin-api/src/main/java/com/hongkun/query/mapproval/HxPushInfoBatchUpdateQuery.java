package com.hongkun.query.mapproval;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName HxUserUndoStatusQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:42
 */
@Data
@ApiModel("鸿信待办-请求参数")
public class HxPushInfoBatchUpdateQuery {


    @ApiModelProperty(value = "批量修改数据")
    private List<HxPushInfoUpdateQuery> batchList;


    @ApiModelProperty(value = "是否删除,0否，1是")
    private Integer deleteStatus;


    @NotNull(message = "待办已办状态不能为空")
    @ApiModelProperty(value = "0已办,1待办")
    private Integer ifTodo;


}
