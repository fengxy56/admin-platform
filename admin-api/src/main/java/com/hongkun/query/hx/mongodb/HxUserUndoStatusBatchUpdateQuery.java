package com.hongkun.query.hx.mongodb;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @ClassName HxUserUndoStatusQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:42
 */
@Data
@ApiModel("鸿信待办-请求参数")
public class HxUserUndoStatusBatchUpdateQuery {


    @ApiModelProperty(value = "批量修改数据")
    private List<HxUserUndoStatusUpdateQuery> batchList;

    @ApiModelProperty(value = "是否删除")
    private boolean delete;

    @NotBlank(message = "待办已办状态不能为空")
    @ApiModelProperty(value = "待办已办状态")
    private String todoStatus;


}
