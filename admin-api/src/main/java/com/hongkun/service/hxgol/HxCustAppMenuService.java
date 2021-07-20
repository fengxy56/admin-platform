package com.hongkun.service.hxgol;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.hx.HxCustAppMenu;
import com.hongkun.model.vo.hx.HxCustAppInfoTreeVO;
import com.hongkun.model.vo.hx.HxCustAppMenuTreeVO;
import com.hongkun.query.hx.HxCustAppInfoQuery;
import com.hongkun.query.hx.HxCustAppMenuSaveQuery;

/**
 * @ClassName HxCustAppMenuService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/30 13:50
 */
public interface HxCustAppMenuService {


    //分页获取应用菜单
    IPage<HxCustAppMenuTreeVO> getHxCustAppMenuList(HxCustAppInfoQuery hxCustAppInfoQuery);

    //保存或者修改菜单
    void saveOrUpdateHxCustAppMenu(HxCustAppMenuSaveQuery hxCustAppMenuSaveQuery);


}
