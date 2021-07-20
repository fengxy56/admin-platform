package com.hongkun.service.admin.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.SysMenu;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.admin.SysMenuMapper;
import com.hongkun.mapper.apply.UserRoleMenuMapper;
import com.hongkun.service.admin.SysMenuService;
import com.hongkun.model.vo.sys.SysMenuVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class SysMenuServiceImpl implements SysMenuService {


    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private UserRoleMenuMapper userRoleMenuMapper;

    @Override
    public List<SysMenuVO> getTreeXMenuList() {

        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("showOrder");
        List<SysMenu> rootMenu = sysMenuMapper.selectList(queryWrapper);

        // 最后的结果
        List<SysMenuVO> menuList = new ArrayList<>();

        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId为-1
            if ("-1".equals(rootMenu.get(i).getParentId())) {

                //第一个是要复制的对下，第二个是复制后的的对象
                SysMenuVO xSysMenuVO = new SysMenuVO();
                BeanUtils.copyProperties(rootMenu.get(i), xSysMenuVO);
                xSysMenuVO.setTitle(xSysMenuVO.getMenuName());
                menuList.add(xSysMenuVO);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysMenuVO sysMenuVO : menuList) {
            sysMenuVO.setChildren(this.getChildByHkMenu(sysMenuVO.getId(), rootMenu));
        }

        return menuList;
    }


    @Override
    public IPage<SysMenu> getXMenuList(Integer pageNo, Integer pageSize, String parentId) {
        //公司实体组织id
        String companyEntitiesId = UserSession.getUser().getCompanyEntitiesId();

        Page<SysMenu> pageWrapper = new Page<>(pageNo, pageSize);
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper();
        queryWrapper.eq("parentId", parentId);
        IPage<SysMenu> xMenus = sysMenuMapper.selectPage(pageWrapper, queryWrapper);
        return xMenus;
    }


    @Transactional(transactionManager = "applyTransactionManager")
    @Override
    public String saveOrUpdate(SysMenu menu) {
        //menu.setUpdatedate(new Date());
        if (StringUtils.isNotBlank(menu.getId())) {
            sysMenuMapper.updateById(menu);
        } else {
            menu.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            sysMenuMapper.insert(menu);
        }
        return menu.getId();
    }

    @Override
    public List<SysMenu> getHkMenuLink() {
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", 1);
        List<SysMenu> xMenus = sysMenuMapper.selectList(queryWrapper);
        return xMenus;
    }


    @Override
    public void deleteXMenuById(String id) {

        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId", id);
        List<SysMenu> xMenuDbList = sysMenuMapper.selectList(queryWrapper);
        if (xMenuDbList.size() > 0) {
            throw new GlobalException(ResultEnum.CHILD_MENU_DELETE_ERROR);
        } else {
            sysMenuMapper.deleteById(id);
        }

    }


    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<SysMenuVO> getChildByHkMenu(String id, List<SysMenu> rootMenu) {
        // 子菜单
        List<SysMenuVO> childList = new ArrayList<>();

        for (SysMenu menu : rootMenu) {
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
