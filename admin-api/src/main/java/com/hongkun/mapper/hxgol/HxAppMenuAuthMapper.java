package com.hongkun.mapper.hxgol;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.hx.HxAppMenuAuth;
import com.hongkun.bean.hx.HxAppAuth;
import com.hongkun.model.vo.hx.AppMenuUserRoleAuthVO;
import com.hongkun.model.vo.hx.AppUserRoleAuthVO;
import com.hongkun.model.vo.hx.UserRoleAppMenuVO;
import com.hongkun.model.vo.hx.UserRoleAppVO;
import com.hongkun.query.hx.AppUserRoleAuthQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName HxAppAuthMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/28 15:19
 */
@Component
public interface HxAppMenuAuthMapper extends BaseMapper<HxAppMenuAuth> {


    List<UserRoleAppMenuVO> getUserRoleAppMenuVOList(@Param("authType") String authType, @Param("authId") String authId, @Param("parentAppId") String parentAppId);


//    List<AppMenuUserRoleAuthVO> getAppMenuUserRoleAuthList(AppUserRoleAuthQuery appUserRoleAuthQuery);

}
