package com.hongkun.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hongkun.bean.SysMenu;
import com.hongkun.bean.apply.SysAuthMenu;
import com.hongkun.bean.apply.SysAuthMenuHistory;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.admin.SysMenuMapper;
import com.hongkun.mapper.apply.SysAuthMenuHistoryMapper;
import com.hongkun.mapper.admin.SysAuthMenuMapper;
import com.hongkun.model.admin.SysOrgVO;
import com.hongkun.model.vo.sys.SysMenuVO;
import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.model.vo.apply.SysAuthMenuVO;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import com.hongkun.service.admin.SysAuthMenuService;
import com.hongkun.until.CodeGeneratedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName SysAuthMenuServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/29 17:31
 */
@Slf4j
@Service
public class SysAuthMenuServiceImpl implements SysAuthMenuService {

    @Resource
    private SysAuthMenuMapper sysAuthMenuMapper;

    @Resource
    private SysAuthMenuHistoryMapper sysAuthMenuHistoryMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;


    /**
     * 根据用户和角色id获取授权列表
     * @return
     */
    @Override
    public List<SysAuthMenuVO> getUserLoginTreeMenu() {


        UvEroleUserVO user = UserSession.getUser();
        String account = user.getAccountName();

        QueryWrapper<SysMenu> xMenuQuery = new QueryWrapper<>();
        xMenuQuery.eq("status", 1);//1有效
        xMenuQuery.lt("type", 2);//0父级菜单，1末级CAD。2是按钮。
        //查询所有菜单列表
        List<SysMenu> xMenuList = sysMenuMapper.selectList(xMenuQuery);


        //根据角色id和版本获取 角色对应授权列表
        List<SysAuthMenuVO> rootMenu = sysAuthMenuMapper.getUserLoginTreeMenu(account, "sys_mainMenu_auth");

        HashSet<SysAuthMenuVO> userXRoleAuthVOSet = new HashSet<>();
        for (SysAuthMenuVO sysAuthMenuVO : rootMenu) {
            //第一个是要复制的对下，第二个是复制后的的对象
            userXRoleAuthVOSet.add(sysAuthMenuVO);

            this.setSysAuthMenuVOSet(xMenuList, sysAuthMenuVO, userXRoleAuthVOSet);
        }

        List<SysAuthMenuVO> sysAuthMenuVOList = new ArrayList<>(userXRoleAuthVOSet);
        Collections.sort(sysAuthMenuVOList, new Comparator<SysAuthMenuVO>() {
            @Override
            public int compare(SysAuthMenuVO o1, SysAuthMenuVO o2) {
                return o1.getShowOrder() - o2.getShowOrder();
            }
        });

        // 最后的结果
        List<SysAuthMenuVO> sysAuthMenuVOListNew = new ArrayList<>();
        // 先找到所有的一级菜单
        for (int i = 0; i < sysAuthMenuVOList.size(); i++) {
            // 一级菜单parentId为-1
            if ("-1".equals(sysAuthMenuVOList.get(i).getParentId())) {
                sysAuthMenuVOListNew.add(sysAuthMenuVOList.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysAuthMenuVO sysAuthMenuVO : sysAuthMenuVOList) {
            sysAuthMenuVO.setChildren(this.getChild(sysAuthMenuVO.getMenuId(), sysAuthMenuVOList));
        }

        return sysAuthMenuVOListNew;
    }


    //根据授权菜单查询匹配所有菜单并且找到所有上级菜单
    private void setSysAuthMenuVOSet(List<SysMenu> xMenuList, SysAuthMenuVO userXRoleAuthVO, HashSet<SysAuthMenuVO> userXRoleAuthVOSet) {
        for (SysMenu menu : xMenuList) {
            //判断菜单是一个,并且
            if (menu.getId().equals(userXRoleAuthVO.getParentId()) && "-1".equals(menu.getParentId())) {
                SysAuthMenuVO sysAuthMenuVONew = new SysAuthMenuVO();
                BeanUtils.copyProperties(menu, sysAuthMenuVONew);
                sysAuthMenuVONew.setMenuId(menu.getId());

                userXRoleAuthVOSet.add(sysAuthMenuVONew);
                break;
            } else if (menu.getId().equals(userXRoleAuthVO.getParentId())) {
                SysAuthMenuVO sysAuthMenuVONew = new SysAuthMenuVO();
                BeanUtils.copyProperties(menu, sysAuthMenuVONew);
                sysAuthMenuVONew.setMenuId(menu.getId());

                userXRoleAuthVOSet.add(sysAuthMenuVONew);
                this.setSysAuthMenuVOSet(xMenuList, sysAuthMenuVONew, userXRoleAuthVOSet);
            }
        }
    }


    /**
     * 根据角色id获取授权列表
     *
     * @return
     */
    @Override
    public List<SysAuthMenuVO> getSysAuthMenuList(SysAuthMenuQuery sysAuthMenuQuery) {
        String authId = sysAuthMenuQuery.getAuthId();
        String authType = sysAuthMenuQuery.getAuthType();
        String sysType = sysAuthMenuQuery.getSysType();

        //根据角色id获取 角色对应授权列表
        List<SysAuthMenuVO> rootMenu = sysAuthMenuMapper.getSysAuthMenuList(authId, authType, sysType);

        // 最后的结果
        List<SysAuthMenuVO> userRoleMenuVOList = new ArrayList<>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId为-1
            if ("-1".equals(rootMenu.get(i).getParentId())) {
                userRoleMenuVOList.add(rootMenu.get(i));
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysAuthMenuVO sysAuthMenuVO : userRoleMenuVOList) {
            sysAuthMenuVO.setChildren(this.getChild(sysAuthMenuVO.getMenuId(), rootMenu));
        }

        return userRoleMenuVOList;
    }

    /**
     * 保存或者修改系统权限菜单
     *
     * @return
     */
    @Override
    @Transactional(transactionManager = "applyTransactionManager")
    public void saveOrUpdateSysAuthMenu(SysAuthMenuQuery sysAuthMenuQuery) {

        String accountName = UserSession.getUser().getAccountName();
        Date currentDate = new Date();


        //获取页面传入的菜单id
        List<String> menuIdList = sysAuthMenuQuery.getMenuIdList();

        //2、首先判断版本id为空，或者不为空并且不是编制中状态,则重新添加角色授权表和版本表
        if (menuIdList != null && menuIdList.size() > 0) {

            QueryWrapper<SysAuthMenu> sysAuthMenuQueryWrapper = new QueryWrapper<>();
            sysAuthMenuQueryWrapper.eq("authId", sysAuthMenuQuery.getAuthId());
            sysAuthMenuQueryWrapper.eq("authType", sysAuthMenuQuery.getAuthType());
            sysAuthMenuQueryWrapper.eq("sysType", sysAuthMenuQuery.getSysType());

            List<SysAuthMenu> xRoleAuthList = sysAuthMenuMapper.selectList(sysAuthMenuQueryWrapper);

            for (SysAuthMenu sysAuthMenu : xRoleAuthList) {
                SysAuthMenuHistory sysAuthMenuHistory = new SysAuthMenuHistory();
                BeanUtils.copyProperties(sysAuthMenu, sysAuthMenuHistory);
                sysAuthMenuHistoryMapper.insert(sysAuthMenuHistory);
                //删除授权表
                sysAuthMenuMapper.deleteById(sysAuthMenu.getId());
            }

            //重新保存角色授权表
            for (String menuId : menuIdList) {
                SysAuthMenu sysAuthMenu = new SysAuthMenu();
                BeanUtils.copyProperties(sysAuthMenuQuery, sysAuthMenu);
                sysAuthMenu.setId(CodeGeneratedUtil.genUUID());
                sysAuthMenu.setMenuId(menuId);
                sysAuthMenu.setSysName(sysAuthMenu.getSysName());
                sysAuthMenu.setUpdateAccount(accountName);
                sysAuthMenu.setCreateTime(currentDate);
                sysAuthMenu.setUpdateTime(currentDate);
                sysAuthMenuMapper.insert(sysAuthMenu);
            }

        } else {//如果存在版本,并且是编制中,直接修改
            throw new GlobalException(ResultEnum.MENU_AUTH_NOT_NULL);
        }

    }


    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysAuthMenuVO> getChild(String id, List<SysAuthMenuVO> rootMenu) {
        // 子菜单
        List<SysAuthMenuVO> childList = new ArrayList<>();

        for (SysAuthMenuVO menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    childList.add(menu);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysAuthMenuVO xMenuVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            xMenuVO.setChildren(this.getChild(xMenuVO.getMenuId(), rootMenu));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


    /**
     * 管理员-获取所有列表
     *
     * @return
     */
    @Override
    public List<SysAuthMenuVO> getAdminUserLoginTreeMenu() {

        UvEroleUserVO user = UserSession.getUser();
        String account = user.getAccountName();
        QueryWrapper<SysMenu> xMenuQuery = new QueryWrapper<>();
        xMenuQuery.eq("status", 1);//1有效
        xMenuQuery.lt("type", 2);//0父级菜单，1末级CAD。2是按钮。
        xMenuQuery.orderByAsc("showOrder");
        //查询所有菜单列表
        List<SysMenu> rootMenu = sysMenuMapper.selectList(xMenuQuery);

        //最终结果
        List<SysAuthMenuVO> sysAuthMenuVOList = new ArrayList<>();


        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId为-1
            if (StringUtils.isNotBlank(rootMenu.get(i).getParentId()) && "-1".equals(rootMenu.get(i).getParentId())) {
                SysAuthMenuVO menuVO = new SysAuthMenuVO();
                BeanUtils.copyProperties(rootMenu.get(i), menuVO);
                menuVO.setMenuId(rootMenu.get(i).getId());
                menuVO.setTitle(rootMenu.get(i).getMenuName());
                sysAuthMenuVOList.add(menuVO);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysAuthMenuVO sysAuthMenuVO : sysAuthMenuVOList) {
            sysAuthMenuVO.setChildren(this.getAdminChild(sysAuthMenuVO.getMenuId(), rootMenu));
        }

        return sysAuthMenuVOList;
    }

    /**
     * 管理员-递归查找子菜单
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysAuthMenuVO> getAdminChild(String id, List<SysMenu> rootMenu) {
        // 子菜单
        List<SysAuthMenuVO> childList = new ArrayList<>();

        for (SysMenu menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {

                    SysAuthMenuVO menuVO = new SysAuthMenuVO();
                    BeanUtils.copyProperties(menu, menuVO);
                    menuVO.setMenuId(menu.getId());
                    menuVO.setTitle(menu.getMenuName());

                    childList.add(menuVO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysAuthMenuVO xMenuVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            xMenuVO.setChildren(this.getAdminChild(xMenuVO.getMenuId(), rootMenu));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }

}
