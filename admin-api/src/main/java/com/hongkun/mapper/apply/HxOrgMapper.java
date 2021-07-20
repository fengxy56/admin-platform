package com.hongkun.mapper.apply;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hongkun.bean.apply.HxOrg;
import com.hongkun.model.vo.apply.HxOrgVO;
import com.hongkun.model.vo.apply.SysAuthMenuVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName HxOrgMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/30 7:17
 */
public interface HxOrgMapper extends BaseMapper<HxOrg> {


    List<HxOrgVO> getHxOrgTreeMenuList(@Param("authId") String authId, @Param("authType") String authType, @Param("sysType") String sysType);


}
