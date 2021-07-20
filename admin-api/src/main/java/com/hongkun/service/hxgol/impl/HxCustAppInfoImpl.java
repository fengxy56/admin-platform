package com.hongkun.service.hxgol.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.hx.HxCustAppInfo;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.hxgol.HxCustAppInfoMapper;
import com.hongkun.query.hx.HxCustAppInfoQuery;
import com.hongkun.query.hx.HxCustAppInfoSaveOrUpdateQuery;
import com.hongkun.service.hxgol.HxCustAppInfoService;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @ClassName HxCustAppInfoImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 19:52
 */
@Service
public class HxCustAppInfoImpl implements HxCustAppInfoService {


    @Autowired
    private HxCustAppInfoMapper hxCustAppInfoMapper;


    //查询轻应用appid
    public IPage<HxCustAppInfo> getHxCustAppInfoList(HxCustAppInfoQuery hxCustAppInfoQuery) {

        Page<HxCustAppInfo> page = new Page<>(hxCustAppInfoQuery.getPageNo(), hxCustAppInfoQuery.getPageSize());

        QueryWrapper<HxCustAppInfo> queryWrapper = new QueryWrapper<>();
        if (hxCustAppInfoQuery.getAppId() != null) {
            queryWrapper.eq("appId", hxCustAppInfoQuery.getAppId());
        }

        if (StringUtils.isNotBlank(hxCustAppInfoQuery.getAppName())) {
            queryWrapper.like("appName", hxCustAppInfoQuery.getAppName());
        }

        if (hxCustAppInfoQuery.getIsTop() != null) {
            queryWrapper.eq("isTop", hxCustAppInfoQuery.getIsTop());
        }

        if (hxCustAppInfoQuery.getStatus() != null) {
            queryWrapper.eq("status", hxCustAppInfoQuery.getStatus());
        }


        if (hxCustAppInfoQuery.getAppType() != null) {
            queryWrapper.eq("appType", hxCustAppInfoQuery.getAppType());
        }

        queryWrapper.orderByDesc("isTop");
        queryWrapper.orderByAsc("showOrder");
//        queryWrapper.eq("orgInfoId",configInfo.getHxOrgInfoId());
        IPage pageList = hxCustAppInfoMapper.selectPage(page, queryWrapper);

        return pageList;
    }


    //保存轻应用appid
    public void saveHxCustAppInfo(HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery) {

        HxCustAppInfo hxCustAppInfo = new HxCustAppInfo();
        BeanUtils.copyProperties(hxCustAppInfoSaveOrUpdateQuery, hxCustAppInfo);


        UvEroleUserVO uvEroleUserVO = UserSession.getUser();
        if (uvEroleUserVO != null) {
            hxCustAppInfo.setUpdateAccout(uvEroleUserVO.getAccountName());
        }

        //校验数据库是否存在当前轻应用
        QueryWrapper<HxCustAppInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", hxCustAppInfo.getAppId());

        List<HxCustAppInfo> hxCustAppInfoList = hxCustAppInfoMapper.selectList(queryWrapper);

        int count = 0;
        if (hxCustAppInfoList != null && hxCustAppInfoList.size() > 0) {
            throw new GlobalException(ResultEnum.SAVE_APPID_EXIST);
        } else {
            QueryWrapper<HxCustAppInfo> appNameWrapper = new QueryWrapper<>();
            appNameWrapper.eq("appName", hxCustAppInfo.getAppName());
            appNameWrapper.ne("appId", hxCustAppInfo.getAppId());
            List<HxCustAppInfo> appNameList = hxCustAppInfoMapper.selectList(appNameWrapper);
            if (appNameList != null && appNameList.size() > 0) {
                throw new GlobalException(ResultEnum.SAVE_APPNAME_EXIST);
            }
            hxCustAppInfo.setCreateTime(new Date());
            hxCustAppInfo.setUpdateTime(new Date());
            count = hxCustAppInfoMapper.insert(hxCustAppInfo);
        }

        if (count < 1) {
            throw new GlobalException(ResultEnum.SAVE_UPDATE_APP_ERROR);
        }

    }


    //修改轻应用appid
    public void updateHxCustAppInfo(HxCustAppInfoSaveOrUpdateQuery hxCustAppInfoSaveOrUpdateQuery) {

        HxCustAppInfo hxCustAppInfo = new HxCustAppInfo();
        BeanUtils.copyProperties(hxCustAppInfoSaveOrUpdateQuery, hxCustAppInfo);


        //校验数据库是否存在当前轻应用
        QueryWrapper<HxCustAppInfo> appIdQueryWrapper = new QueryWrapper<>();
        appIdQueryWrapper.eq("appId", hxCustAppInfo.getAppId());

        List<HxCustAppInfo> hxCustAppInfoList = hxCustAppInfoMapper.selectList(appIdQueryWrapper);

        int count = 0;
        if (hxCustAppInfoList != null && hxCustAppInfoList.size() > 0) {

            QueryWrapper<HxCustAppInfo> appNameWrapper = new QueryWrapper<>();
            appNameWrapper.eq("appName", hxCustAppInfo.getAppName());
            appNameWrapper.ne("appId", hxCustAppInfo.getAppId());
            List<HxCustAppInfo> appNameList = hxCustAppInfoMapper.selectList(appNameWrapper);
            if (appNameList != null && appNameList.size() > 0) {
                throw new GlobalException(ResultEnum.UPDATE_APPNAME_EXIST);
            }


            UvEroleUserVO uvEroleUserVO = UserSession.getUser();
            if (uvEroleUserVO != null) {
                hxCustAppInfo.setUpdateAccout(uvEroleUserVO.getAccountName());
            }
            hxCustAppInfo.setUpdateTime(new Date());
            count = hxCustAppInfoMapper.update(hxCustAppInfo, appIdQueryWrapper);
        } else {
            throw new GlobalException(ResultEnum.UPDATE_APPID_EXIST);
        }

        if (count < 1) {
            throw new GlobalException(ResultEnum.SAVE_UPDATE_APP_ERROR);
        }

    }

}
