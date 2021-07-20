package com.hongkun.mapper.admin;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.SysAuthMenu;
import com.hongkun.model.vo.apply.SysAuthMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName SysAuthMenuMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/29 17:20
 */
public interface SysAuthMenuMapper extends BaseMapper<SysAuthMenu> {


    //根据权限id,权限类型,系统类型
    List<SysAuthMenuVO> getUserLoginTreeMenu(@Param("account") String account, @Param("sysType") String sysType);


    //根据权限id,权限类型,系统类型
    List<SysAuthMenuVO> getSysAuthMenuList(@Param("authId") String authId, @Param("authType") String authType, @Param("sysType") String sysType);

}
