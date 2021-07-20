package com.hongkun.service.apply.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.SysInfo;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.apply.SysInfoMapper;
import com.hongkun.query.SysInfoQuery;
import com.hongkun.service.apply.SysInfoService;
import com.hongkun.until.Base64Util;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.until.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysInfoServiceImpl implements SysInfoService {


    @Autowired
    private SysInfoMapper sysInfoMapper;

    @Override
    public IPage<SysInfo> getSysInfoList(Integer pageNo, Integer pageSize, Integer status, String sysName) {

        Page<SysInfo> pageWrapper = new Page<>(pageNo, pageSize);
        QueryWrapper<SysInfo> queryWrapper = new QueryWrapper();

        if (status != null) {
            queryWrapper.eq("status", status);
        }
        if (StringUtils.isNotBlank(sysName)) {
            queryWrapper.like("sysName", sysName);
        }
        queryWrapper.orderByDesc("updateTime");
        IPage<SysInfo> sysInfoPage = sysInfoMapper.selectPage(pageWrapper, queryWrapper);

        return sysInfoPage;
    }


    @Override
    public void saveOrUpdateSysInfo(SysInfoQuery sysInfoQuery) {

        int count = 0;

        SysInfo sysInfo = new SysInfo();
        BeanUtils.copyProperties(sysInfoQuery, sysInfo);
        sysInfo.setUpdateTime(new Date());
        sysInfo.setUpdateAccount(UserSession.getUser().getAccountName());
        try {
            String sysSercret = MD5Util.getMD5(sysInfoQuery.getSysCode());
            sysInfo.setSysSercret(sysSercret);
            sysInfo.setToken(Base64Util.toBase64Str(sysInfoQuery.getSysCode() + ":" + sysSercret));
        } catch (MD5Util.MD5Exception e) {
            throw new GlobalException(ResultEnum.MD5_ENCRY_FAIL);
        }

        //判断传递的id是否为空
        if (StringUtils.isBlank(sysInfoQuery.getId())) {
            sysInfo.setId(CodeGeneratedUtil.genUUID());
            sysInfo.setCreateTime(new Date());
            count = sysInfoMapper.insert(sysInfo);
        } else {


            QueryWrapper<SysInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("sysCode", sysInfoQuery.getSysCode());
            List<SysInfo> sysInfoList = sysInfoMapper.selectList(queryWrapper);
            if (sysInfoList != null && sysInfoList.size() > 0) {
                throw new GlobalException(ResultEnum.UPDATE_SYS_INFO);
            }


            sysInfo.setId(sysInfoQuery.getId());
            count = sysInfoMapper.updateById(sysInfo);
        }

        if (count < 1) {
            throw new GlobalException(ResultEnum.SAVEORUPDATE_SYSINFO_FAIL);
        }

    }


}
