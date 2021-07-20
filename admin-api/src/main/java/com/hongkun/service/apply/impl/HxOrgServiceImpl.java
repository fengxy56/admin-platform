package com.hongkun.service.apply.impl;

import com.hongkun.mapper.apply.HxOrgMapper;
import com.hongkun.query.apply.SysAuthMenuQuery;
import com.hongkun.model.vo.apply.HxOrgVO;
import com.hongkun.service.apply.HxOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HxOrgServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/30 7:18
 */
@Service
public class HxOrgServiceImpl implements HxOrgService {

    @Resource
    private HxOrgMapper hxOrgMapper;


    //获取组织数结构
    public List<HxOrgVO> getHxOrgTreeList(SysAuthMenuQuery sysAuthMenuQuery) {

        List<HxOrgVO> rootMenu = hxOrgMapper.getHxOrgTreeMenuList(sysAuthMenuQuery.getAuthId(), sysAuthMenuQuery.getAuthType(), sysAuthMenuQuery.getSysType());
        // 最后的结果
        List<HxOrgVO> hxOrgVOList = new ArrayList<>();


        // 先找到所有的一级菜单
        for (int i = 0; i < rootMenu.size(); i++) {
            // 一级菜单parentId为-1
            if (StringUtils.isBlank(rootMenu.get(i).getParentId()) || "商务伙伴".equals(rootMenu.get(i).getDepartment())) {
                HxOrgVO hxOrgVO = new HxOrgVO();
                BeanUtils.copyProperties(rootMenu.get(i), hxOrgVO);
//                hxOrgVO.setMenuId(hxOrgVO.getId());
//                hxOrgVO.setMenuName(hxOrgVO.getName());
//                hxOrgVO.setTitle(hxOrgVO.getName());
                hxOrgVOList.add(hxOrgVO);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (HxOrgVO hxOrgVO : hxOrgVOList) {
            hxOrgVO.setChildren(this.getChild(hxOrgVO.getMenuId(), rootMenu));
        }

        return hxOrgVOList;
    }


    /**
     * 递归查找子菜单
     *
     * @param id       当前菜单id
     * @param rootMenu 要查找的列表
     * @return
     */
    private List<HxOrgVO> getChild(String id, List<HxOrgVO> rootMenu) {
        // 子菜单
        List<HxOrgVO> childList = new ArrayList<>();

        for (HxOrgVO menu : rootMenu) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(menu.getParentId())) {
                if (menu.getParentId().equals(id)) {
                    HxOrgVO hxOrgVO = new HxOrgVO();
                    BeanUtils.copyProperties(menu, hxOrgVO);
//                    hxOrgVO.setMenuId(hxOrgVO.getId());
//                    hxOrgVO.setMenuName(hxOrgVO.getName());
//                    hxOrgVO.setTitle(hxOrgVO.getName());
                    childList.add(hxOrgVO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (HxOrgVO menuVO : childList) {// 没有url子菜单还有子菜单
            // 递归
            menuVO.setChildren(this.getChild(menuVO.getMenuId(), rootMenu));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


//    public List<HxOrg> get(String parentId){
//        QueryWrapper<HxOrg> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("parentId",parentId);
//
//        List<HxOrg> list = hxOrgMapper.selectList(queryWrapper);
//
//    }


}
