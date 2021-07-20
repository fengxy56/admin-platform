package com.hongkun.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.HxSignTrackAuth;
import com.hongkun.query.apply.HxSignTrackAuthQuery;
import com.hongkun.model.vo.apply.HxSignTrackAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName HxSignTrackAuthMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/16 17:06
 */
@Component
public interface HxSignTrackAuthMapper extends BaseMapper<HxSignTrackAuth> {


    List<UserRoleAppVO> getUserRoleAppList(@Param("authType") String authType, @Param("authId") String authId);


    List<HxSignTrackAuthVO> getHxSignTrackAuthList(HxSignTrackAuthQuery hxSignTrackAuthQuery);

}
