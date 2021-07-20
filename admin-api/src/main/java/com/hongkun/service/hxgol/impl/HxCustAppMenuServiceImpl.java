package com.hongkun.service.hxgol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.hx.HxCustAppInfo;
import com.hongkun.bean.hx.HxCustAppMenu;
import com.hongkun.commons.UserSession;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.hxgol.HxCustAppInfoMapper;
import com.hongkun.mapper.hxgol.HxCustAppMenuMapper;
import com.hongkun.model.vo.hx.HxCustAppMenuTreeVO;
import com.hongkun.query.hx.HxCustAppInfoQuery;
import com.hongkun.query.hx.HxCustAppMenuSaveQuery;
import com.hongkun.service.hxgol.HxCustAppMenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxCustAppMenuServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/30 13:50
 */
@Service
public class HxCustAppMenuServiceImpl implements HxCustAppMenuService {


    @Resource
    private HxCustAppInfoMapper hxCustAppInfoMapper;

    @Resource
    private HxCustAppMenuMapper hxCustAppMenuMapper;

    //@Override
    public IPage<HxCustAppMenuTreeVO> getHxCustAppMenuList(HxCustAppInfoQuery hxCustAppInfoQuery) {

        Page<HxCustAppMenu> page = new Page<>(hxCustAppInfoQuery.getPageNo(),hxCustAppInfoQuery.getPageSize());
        QueryWrapper<HxCustAppMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("parentAppId", hxCustAppInfoQuery.getAppId());
//        queryWrapper.eq("level", 1);
//        if (StringUtils.isNotBlank(dictGroup)) {
//            queryWrapper.like("dictGroup", dictGroup);
//        }
//        if (StringUtils.isNotBlank(dicName)) {
//            queryWrapper.like("dictName", dicName);
//        }
        page.setAsc("showOrder");
        IPage<HxCustAppMenu> hxCustAppInfoIPage = hxCustAppMenuMapper.selectPage(page, queryWrapper);
        List<HxCustAppMenu> hxCustAppInfoList = hxCustAppInfoIPage.getRecords();

        List<HxCustAppMenuTreeVO> finalSysDictTreeVOList = new ArrayList<>();
        if(hxCustAppInfoList !=null && hxCustAppInfoList.size() >0){
            for(HxCustAppMenu hxCustAppInfo : hxCustAppInfoList){
                HxCustAppMenuTreeVO hxCustAppMenuTreeVO = new HxCustAppMenuTreeVO();
                BeanUtils.copyProperties(hxCustAppInfo,hxCustAppMenuTreeVO);
                hxCustAppMenuTreeVO.setChildren(this.treeHxCustAppMenuTreeVO(hxCustAppMenuTreeVO));

                finalSysDictTreeVOList.add(hxCustAppMenuTreeVO);
            }
        }

        IPage<HxCustAppMenuTreeVO> hxCustAppMenuTreeVOPage = new Page<>();
        ;
        BeanUtils.copyProperties(hxCustAppInfoIPage, hxCustAppMenuTreeVOPage);
        hxCustAppMenuTreeVOPage.setRecords(finalSysDictTreeVOList);
        return hxCustAppMenuTreeVOPage;
    }



    public List<HxCustAppMenuTreeVO> treeHxCustAppMenuTreeVO(HxCustAppMenuTreeVO hxCustAppMenuTreeVO) {
        QueryWrapper<HxCustAppMenu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentAppId",hxCustAppMenuTreeVO.getAppId());
        queryWrapper.orderByAsc("showorder");
        List<HxCustAppMenu> hxCustAppMenuList = hxCustAppMenuMapper.selectList(queryWrapper);

        List<HxCustAppMenuTreeVO> hxCustAppMenuTreeVOList = new ArrayList<>();

        if (hxCustAppMenuList != null && hxCustAppMenuList.size() > 0) {
            for (HxCustAppMenu hxCustAppMenu : hxCustAppMenuList) {

                if (hxCustAppMenu != null) {

                    HxCustAppMenuTreeVO hxCustAppMenuTreeVONew = new HxCustAppMenuTreeVO();
                    BeanUtils.copyProperties(hxCustAppMenu,hxCustAppMenuTreeVONew);
                    hxCustAppMenuTreeVONew.setChildren(this.treeHxCustAppMenuTreeVO(hxCustAppMenuTreeVONew));
                    hxCustAppMenuTreeVOList.add(hxCustAppMenuTreeVONew);
                }
            }
        } else {
            return null;
        }
        return hxCustAppMenuTreeVOList;
    }



    public void saveOrUpdateHxCustAppMenu(HxCustAppMenuSaveQuery hxCustAppMenuSaveQuery){

        HxCustAppMenu hxCustAppMenu = new HxCustAppMenu();
        BeanUtils.copyProperties(hxCustAppMenuSaveQuery,hxCustAppMenu);
        hxCustAppMenu.setUpdateAccout(UserSession.getUser().getAccountName());
        hxCustAppMenu.setUpdateName(UserSession.getUser().getChsName());


        if(StringUtils.isNotBlank(hxCustAppMenuSaveQuery.getActionType()) && "add".equals(hxCustAppMenuSaveQuery.getActionType())){

            HxCustAppMenu hxCustAppMenuDb = hxCustAppMenuMapper.selectById(hxCustAppMenuSaveQuery.getAppId());
            //存在
            if(hxCustAppMenuDb !=null && StringUtils.isNotBlank(hxCustAppMenuDb.getAppId())){
                throw new GlobalException(0,"该应用appId已经存在");
            }

            hxCustAppMenu.setCreateTime(new Date());
            hxCustAppMenu.setUpdateTime(new Date());
            hxCustAppMenuMapper.insert(hxCustAppMenu);

        }else{

            hxCustAppMenu.setUpdateTime(new Date());
            hxCustAppMenuMapper.updateById(hxCustAppMenu);

        }



    }






}
