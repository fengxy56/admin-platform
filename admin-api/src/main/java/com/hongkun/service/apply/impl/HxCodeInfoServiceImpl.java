package com.hongkun.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.HxCodeInfo;
import com.hongkun.commons.FileEnum;
import com.hongkun.config.ConfigInfo;
import com.hongkun.mapper.apply.HxCodeInfoMapper;
import com.hongkun.query.apply.HxCodeInfoQuery;
import com.hongkun.service.apply.HxCodeInfoService;
import com.hongkun.until.WorkUntil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxCodeInfoServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/27 14:30
 */
@Service
public class HxCodeInfoServiceImpl implements HxCodeInfoService {

    @Resource
    private HxCodeInfoMapper hxCodeInfoMapper;

    @Resource
    private ConfigInfo configInfo;


    public IPage<HxCodeInfo> getHxCodeInfoList(HttpServletRequest request, HxCodeInfoQuery hxCodeInfoQuery) {

        Page<HxCodeInfo> page = new Page<>(hxCodeInfoQuery.getPageNo(), hxCodeInfoQuery.getPageSize());

        QueryWrapper<HxCodeInfo> queryWrapper = new QueryWrapper<>();
//        if(StringUtils.isNotBlank(hxCodeInfoQuery.getPositionName())){
//            queryWrapper.like("positionName",hxCodeInfoQuery.getPositionName());
//        }
//        if(StringUtils.isNotBlank(hxCodeInfoQuery.getAddress())){
//            queryWrapper.like("address",hxCodeInfoQuery.getAddress());
//        }
//        if(StringUtils.isNotBlank(hxCodeInfoQuery.getAlias())){
//            queryWrapper.eq("alias",hxCodeInfoQuery.getAlias());
//        }
        queryWrapper.orderByDesc("updateTime");
        IPage<HxCodeInfo> pageList = hxCodeInfoMapper.selectPage(page, queryWrapper);
        List<HxCodeInfo> list = pageList.getRecords();
        if (list != null && list.size() > 0) {
            for (HxCodeInfo hxCodeInfo : list) {
                if (StringUtils.isNotBlank(hxCodeInfo.getDownUrl())) {
                    hxCodeInfo.setDownUrl(WorkUntil.getFileUrl("", hxCodeInfo.getDownUrl(), FileEnum.FILE_ENUM_IMAGE, "0"));
                }

            }
        }

        return pageList;
    }

    public void saveOrUpdateHxCodeInfo(HxCodeInfoQuery hxCodeInfoQuery) {
        HxCodeInfo hxCodeInfo = new HxCodeInfo();
        BeanUtils.copyProperties(hxCodeInfoQuery, hxCodeInfo);
        hxCodeInfo.setUpdateTime(new Date());

        HxCodeInfo hxCodeInfoDb = hxCodeInfoMapper.selectById(hxCodeInfoQuery.getId());
        if (hxCodeInfoDb != null) {

            hxCodeInfoMapper.updateById(hxCodeInfo);

        } else {
            hxCodeInfo.setCreateTime(new Date());
            hxCodeInfoMapper.insert(hxCodeInfo);
        }

    }

}
