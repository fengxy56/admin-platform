package com.hongkun.model.vo.page;

import com.hongkun.model.vo.apply.HxSignTrackRecordVO;
import com.hongkun.model.vo.hx.LogHxSiteVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @ClassName LogHxSitePageVO
 * @Description 这里描述
 * @Author admin
 * @Date 2020/4/7 13:58
 */
@ApiModel(value = "智慧工地分页返回数据")
@Data
public class HxSignTrackRecordPageVO {

    @ApiModelProperty(value = "分页记录列表")
    List<HxSignTrackRecordVO> records;

    @ApiModelProperty(value = "当前页")
    private long current;

    //每页的数量
    @ApiModelProperty(value = "活动类型名称")
    private long size;

    //当前页的数量
    @ApiModelProperty(value = "总页数")
    private long pages;

    //当前页的数量
    @ApiModelProperty(value = "总数量")
    private long total;


}
