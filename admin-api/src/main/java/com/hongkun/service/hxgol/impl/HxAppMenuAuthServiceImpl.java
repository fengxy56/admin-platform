package com.hongkun.service.hxgol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.apply.Menu;
import com.hongkun.bean.hx.HxAppMenuAuth;
import com.hongkun.commons.ResultEnum;

import com.hongkun.commons.UserSession;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.hxgol.HxAppMenuAuthMapper;
import com.hongkun.model.vo.sys.SysMenuVO;
import com.hongkun.model.vo.hx.UserRoleAppMenuVO;
import com.hongkun.query.hx.HxUserRoleAppAuthQuery;
import com.hongkun.service.hxgol.HxAppMenuAuthService;
import com.hongkun.until.CodeGeneratedUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxAppMenuAuthServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/28 16:08
 */
@Component
public class HxAppMenuAuthServiceImpl implements HxAppMenuAuthService {


    @Autowired
    private HxAppMenuAuthMapper hxAppMenuAuthMapper;



    /**
     * 根据角色id获取授权用户列表
     *
     * @return
     */
    @Override
    @Transactional(transactionManager = "hxgolTransactionManager")
    public void saveUserRoleHxAppMenuAuth(HxUserRoleAppAuthQuery hxUserRoleAppAuthQuery) {

        String accountName = UserSession.getUser().getAccountName();
        Date currentDate = new Date();


        //获取页面传入的菜单id
        List<String> menuIdList = hxUserRoleAppAuthQuery.getMenuIdList();

        //2、首先判断版本id为空，或者不为空并且不是编制中状态,则重新添加角色授权表和版本表
        if (menuIdList != null && menuIdList.size() > 0) {

            QueryWrapper<HxAppMenuAuth> userMenuQuery = new QueryWrapper<>();
            userMenuQuery.eq("authId", hxUserRoleAppAuthQuery.getAuthId());
            userMenuQuery.eq("authType", hxUserRoleAppAuthQuery.getAuthType());
            //删除授权表
            hxAppMenuAuthMapper.delete(userMenuQuery);

            //重新保存角色授权表
            for (String menuId : menuIdList) {
                HxAppMenuAuth hxAppMenuAuth = new HxAppMenuAuth();
                hxAppMenuAuth.setId(CodeGeneratedUtil.genUUID());
                hxAppMenuAuth.setAppId(Integer.parseInt(menuId));
                hxAppMenuAuth.setAuthId(hxUserRoleAppAuthQuery.getAuthId());
                hxAppMenuAuth.setAuthType(hxUserRoleAppAuthQuery.getAuthType());
                hxAppMenuAuth.setUpdateAccount(accountName);
                hxAppMenuAuth.setCreateTime(currentDate);
                hxAppMenuAuth.setUpdateTime(currentDate);
                hxAppMenuAuthMapper.insert(hxAppMenuAuth);
            }

        } else {//如果存在版本,并且是编制中,直接修改
            throw new GlobalException(ResultEnum.SAVE_USERROLE_APP_NOTEXIST);
        }

    }



    //获取用户角色授权列表
    public List<UserRoleAppMenuVO> getUserRoleAppMenuVOList(String authType, String authId,String parentAppId) {

        List<UserRoleAppMenuVO> userRoleAppMenuVOList = hxAppMenuAuthMapper.getUserRoleAppMenuVOList(authType, authId,parentAppId);

        // 先找到所有的一级菜单
        for (UserRoleAppMenuVO userRoleAppMenuVO : userRoleAppMenuVOList) {
            userRoleAppMenuVO.setChildren(this.treeHxCustAppMenuTreeVO(authType, authId,userRoleAppMenuVO.getMenuId()));
        }

        return userRoleAppMenuVOList;
    }

    public List<UserRoleAppMenuVO> treeHxCustAppMenuTreeVO(String authType, String authId,String parentAppId) {

        List<UserRoleAppMenuVO> userRoleAppMenuVOList = hxAppMenuAuthMapper.getUserRoleAppMenuVOList(authType, authId,parentAppId);

        if (userRoleAppMenuVOList != null && userRoleAppMenuVOList.size() > 0) {
            for (UserRoleAppMenuVO userRoleAppMenuVO : userRoleAppMenuVOList) {
                if (userRoleAppMenuVO != null) {
                    userRoleAppMenuVO.setChildren(this.treeHxCustAppMenuTreeVO(authType,authId,userRoleAppMenuVO.getMenuId()));
                }
            }
        } else {
            return null;
        }
        return userRoleAppMenuVOList;
    }


    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysMenuVO> getChildByHkMenu(String id, List<Menu> rootMenu) {
        // 子菜单
        List<SysMenuVO> childList = new ArrayList<>();

        for (Menu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {

                    //第一个是要复制的对下，第二个是复制后的的对象
                    SysMenuVO sysMenuVO = new SysMenuVO();
                    BeanUtils.copyProperties(menu, sysMenuVO);
                    sysMenuVO.setTitle(sysMenuVO.getMenuName());
                    childList.add(sysMenuVO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysMenuVO sysMenuVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            sysMenuVO.setChildren(this.getChildByHkMenu(sysMenuVO.getId(), rootMenu));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
