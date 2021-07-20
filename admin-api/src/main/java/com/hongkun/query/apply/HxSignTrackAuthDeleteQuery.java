package com.hongkun.query.apply;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Description: null
 *
 * @author fengxiaoyang
 * @date 2020-3-25 8:23:14
 */
@Data
@ApiModel("签到轨迹-取消用户角色授权-请求参数")
public class HxSignTrackAuthDeleteQuery implements Serializable {

    @ApiModelProperty(value = "轨迹权限列表")
    private List<HxSignTrackAuthQuery> authList;

}
