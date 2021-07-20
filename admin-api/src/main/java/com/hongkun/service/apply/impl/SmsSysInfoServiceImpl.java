package com.hongkun.service.apply.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.SmsSysInfo;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.apply.SmsSysInfoMapper;
import com.hongkun.query.SmsSysInfoQuery;
import com.hongkun.service.apply.SmsSysInfoService;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.until.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SmsSysInfoServiceImpl implements SmsSysInfoService {


    @Autowired
    private SmsSysInfoMapper smsSysInfoMapper;

    @Override
    public IPage<SmsSysInfo> getSmsSysInfoList(Integer pageNo, Integer pageSize, Integer status, String sysName) {

        Page<SmsSysInfo> pageWrapper = new Page<>(pageNo, pageSize);
        QueryWrapper<SmsSysInfo> queryWrapper = new QueryWrapper<>();
        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (StringUtils.isNotBlank(sysName)) {
            queryWrapper.like("sysName", sysName);
        }
        queryWrapper.orderByDesc("updateTime");

        IPage<SmsSysInfo> smsSysInfoPage = smsSysInfoMapper.selectPage(pageWrapper, queryWrapper);
        return smsSysInfoPage;
    }

    @Override
    public void saveOrUpdateSmsSysInfo(SmsSysInfoQuery smsSysInfoQuery) {
        int saveOrUpdateCount = 0;

        SmsSysInfo smsSysInfo = new SmsSysInfo();

        BeanUtils.copyProperties(smsSysInfoQuery, smsSysInfo);
        smsSysInfo.setUpdateTime(new Date());
        smsSysInfo.setUpdateAccount(UserSession.getUser().getAccountName());


        try {
            String smsSecret = MD5Util.getMD5(smsSysInfoQuery.getSmsKey());
            smsSysInfo.setSmsSecret(smsSecret);
        } catch (MD5Util.MD5Exception e) {
            throw new GlobalException(ResultEnum.MD5_ENCRY_FAIL);
        }


        //判断传递的id是否为空
        if (StringUtils.isBlank(smsSysInfoQuery.getId())) {
            smsSysInfo.setId(CodeGeneratedUtil.genUUID());
            smsSysInfo.setCreateTime(new Date());
            smsSysInfo.setUpdateTime(new Date());
            saveOrUpdateCount = smsSysInfoMapper.insert(smsSysInfo);
        } else {
            smsSysInfo.setId(smsSysInfoQuery.getId());
            smsSysInfo.setUpdateTime(new Date());
            saveOrUpdateCount = smsSysInfoMapper.updateById(smsSysInfo);
        }

        if (saveOrUpdateCount < 1) {
            throw new GlobalException(ResultEnum.SAVEORUPDATE_SMSSYSINFO_FAIL);
        }

    }


}
