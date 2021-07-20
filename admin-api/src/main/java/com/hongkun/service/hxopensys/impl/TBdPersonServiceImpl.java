package com.hongkun.service.hxopensys.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.hx.TBdPerson;
import com.hongkun.config.ConfigInfo;
import com.hongkun.mapper.hxopensys.TBdPersonMapper;
import com.hongkun.query.hx.TBdPersonQuery;
import com.hongkun.query.hx.TBdPersonUpdateQuery;
import com.hongkun.service.hxopensys.TBdPersonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;

/**
 * @ClassName TBdPersonServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 7:40
 */
@Service
public class TBdPersonServiceImpl implements TBdPersonService {

    @Autowired
    private TBdPersonMapper tBdPersonMapper;

    @Autowired
    private ConfigInfo configInfo;

    public IPage<TBdPerson> getTBdPersonList(TBdPersonQuery tBdPersonQuery) {

        Page<TBdPerson> page = new Page<>(tBdPersonQuery.getPageNo(), tBdPersonQuery.getPageSize());

        QueryWrapper<TBdPerson> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(tBdPersonQuery.getPhone())) {
            queryWrapper.like("phone", tBdPersonQuery.getPhone());
        }

        if (StringUtils.isNotBlank(tBdPersonQuery.getName())) {
            queryWrapper.like("name", tBdPersonQuery.getName());
        }
        queryWrapper.eq("orgInfoId", configInfo.getHxOrgInfoId());
        queryWrapper.orderByDesc("lastUpdateTime");
        IPage pageList = tBdPersonMapper.selectPage(page, queryWrapper);

        return pageList;

    }


    //修改用户最后时间
    public void updateTBdPersonLastUpdateTime(TBdPersonUpdateQuery tBdPersonUpdateQuery) {

        QueryWrapper<TBdPerson> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(tBdPersonUpdateQuery.getOid())) {
            queryWrapper.like("oid", tBdPersonUpdateQuery.getOid());
        }
        if (StringUtils.isNotBlank(tBdPersonUpdateQuery.getPhone())) {
            queryWrapper.like("phone", tBdPersonUpdateQuery.getPhone());
        }
        if (tBdPersonUpdateQuery.getStatus() != null) {
            queryWrapper.like("status", tBdPersonUpdateQuery.getStatus());
        }
        queryWrapper.eq("orgInfoId", configInfo.getHxOrgInfoId());


        TBdPerson tBdPerson = new TBdPerson();
        tBdPerson.setLastUpdateTime(new Date());
        tBdPersonMapper.update(tBdPerson, queryWrapper);

    }

}
