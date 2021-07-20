package com.hongkun.query.hx;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName HxPollImageSaveOrUpdateQuery
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 19:19
 */
@Data
public class HxPollImageSaveOrUpdateQuery {


    @ApiModelProperty(value = "要删除的轮播图id集合")
    private List<String> deletedHxPollImageList;

    @ApiModelProperty(value = "轮播图集合")
    private List<HxPollImageVO> hxPollImageList;

}
