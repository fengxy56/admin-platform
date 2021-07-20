package com.hongkun.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.SysMenu;
import com.hongkun.model.vo.sys.SysMenuVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface SysMenuService {


    /**
     * @Description 获取菜单树状结构
     * @Author fengxiaoyang
     * @Date 2020-03-31
     */
    List<SysMenuVO> getTreeXMenuList();


    /**
     * @param pageNo   页码
     * @param pageSize 条数
     * @param parentId 菜单父级id
     * @return
     * @Description 通过父级id分页查询
     * @Author fengxiaoyang
     * @Date 2020-03-31
     */
    IPage<SysMenu> getXMenuList(Integer pageNo, Integer pageSize, String parentId);

    /**
     * 保存或者修改菜单
     *
     * @return
     */
    String saveOrUpdate(SysMenu menu);


    /**
     * 获取末级菜单
     *
     * @return
     */
    List<SysMenu> getHkMenuLink();

    /**
     * @param id 菜单id
     * @return
     * @Description 根据菜单id菜单
     * @Author fengxiaoyang
     * @Date 2020-03-31
     */
    void deleteXMenuById(String id);


}
