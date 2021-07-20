package com.hongkun.mapper.apply;

import com.hongkun.model.vo.UserRoleMenuVO;
import com.hongkun.model.vo.uc.UserXRoleAuthVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName UserRoleMenuMapper
 * @Description 这里描述
 * @Author admin
 * @Date 2020/4/14 9:25
 */
@Component
public interface UserRoleMenuMapper {

    List<UserRoleMenuVO> getUserLoginTreeMenu(@Param("roleIdList") List<String> roleIdList, @Param("account") String account);

    List<UserRoleMenuVO> getRoleMenuListByRoleId(@Param("roleId") String roleId);

    List<UserRoleMenuVO> getUserMenuListByAccount(@Param("account") String account);

}
