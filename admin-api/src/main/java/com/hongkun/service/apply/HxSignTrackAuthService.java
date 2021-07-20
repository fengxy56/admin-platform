package com.hongkun.service.apply;

import com.hongkun.query.apply.HxSignTrackAuthDeleteQuery;
import com.hongkun.query.apply.HxSignTrackAuthQuery;
import com.hongkun.query.apply.HxSignTrackBathAuthQuery;
import com.hongkun.model.vo.apply.HxSignTrackAuthVO;

import java.util.List;

/**
 * @ClassName HxSignTrackAuthService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/16 17:07
 */
public interface HxSignTrackAuthService {

    //保存批量用户角色授权
    String saveBathHxSignTrackBathAuth(HxSignTrackBathAuthQuery hxSignTrackBathAuthQuery);

    //获取appid的用户角色授权列表
    List<HxSignTrackAuthVO> getHxSignTrackAuthList(HxSignTrackAuthQuery hxSignTrackAuthQuery);

    //取消用户角色授权列表
    Integer deleteHxSignTrackAuthList(HxSignTrackAuthDeleteQuery hxSignTrackAuthDeleteQuery);

}
