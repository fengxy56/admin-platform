package com.hongkun.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.HxSignTrackConfigure;
import com.hongkun.commons.UserSession;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.apply.HxSignTrackConfigureMapper;
import com.hongkun.query.apply.HxSignTrackConfigureQuery;
import com.hongkun.query.apply.HxSignTrackConfigureSaveQuery;
import com.hongkun.service.apply.HxSignTrackConfigureService;
import com.hongkun.until.CodeGeneratedUtil;
import com.hongkun.until.GetDistanceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName HxSignTrackConfigureServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/19 13:55
 */
@Service
public class HxSignTrackConfigureServiceImpl implements HxSignTrackConfigureService {


    @Autowired
    private HxSignTrackConfigureMapper hxSignTrackConfigureMapper;

    //获取签到地点配置文件
    public IPage<HxSignTrackConfigure> getHxSignTrackConfigureList(HxSignTrackConfigureQuery hxSignTrackConfigureQuery) {

        Page<HxSignTrackConfigure> page = new Page<>(hxSignTrackConfigureQuery.getPageNo(), hxSignTrackConfigureQuery.getPageSize());

        QueryWrapper<HxSignTrackConfigure> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(hxSignTrackConfigureQuery.getPositionName())) {
            queryWrapper.like("positionName", hxSignTrackConfigureQuery.getPositionName());
        }
        if (StringUtils.isNotBlank(hxSignTrackConfigureQuery.getAddress())) {
            queryWrapper.like("address", hxSignTrackConfigureQuery.getAddress());
        }
        if (StringUtils.isNotBlank(hxSignTrackConfigureQuery.getAlias())) {
            queryWrapper.eq("alias", hxSignTrackConfigureQuery.getAlias());
        }
        queryWrapper.orderByDesc("updateTime");
        IPage<HxSignTrackConfigure> pageList = hxSignTrackConfigureMapper.selectPage(page, queryWrapper);
        return pageList;
    }

    //保存签到地点配置文件
    public int saveOrUpdateHxSignTrackConfigure(HxSignTrackConfigureSaveQuery hxSignTrackConfigureSaveQuery) {
        int count = 0;


        //判断签到点是否交叉相近
//        List<HxSignTrackConfigure> hxSignTrackConfigureList = hxSignTrackConfigureMapper.selectList(null);
//        if (hxSignTrackConfigureList != null && hxSignTrackConfigureList.size() > 0) {
//
//            for (HxSignTrackConfigure hxSignTrackConfigure : hxSignTrackConfigureList) {
//
//                double diffOffset = GetDistanceUtils.algorithm(hxSignTrackConfigure.getLongitude(), hxSignTrackConfigure.getLatitude()
//                        , hxSignTrackConfigureSaveQuery.getLongitude(), hxSignTrackConfigureSaveQuery.getLatitude());
//
//                //偏移量长度
//                double newOffset = hxSignTrackConfigure.getOffset() + hxSignTrackConfigureSaveQuery.getOffset();
//                //如果新设置的签到点和原又签到点之间的距离，
//                if (!hxSignTrackConfigure.getId().equals(hxSignTrackConfigureSaveQuery.getId()) && newOffset > diffOffset) {
//                    throw new GlobalException(ResultEnum.SIGN_DISTANCE_CLOSE);
//                }
//
//            }
//        }


        HxSignTrackConfigure hxSignTrackConfigure = new HxSignTrackConfigure();
        BeanUtils.copyProperties(hxSignTrackConfigureSaveQuery, hxSignTrackConfigure);

        if (StringUtils.isNotBlank(hxSignTrackConfigure.getId())) {
            hxSignTrackConfigure.setUpdateTime(new Date());
            hxSignTrackConfigure.setUpdateUserName(UserSession.getUser().getAccountName());

            count = hxSignTrackConfigureMapper.updateById(hxSignTrackConfigure);
        } else {
            hxSignTrackConfigure.setId(CodeGeneratedUtil.genUUID());
            hxSignTrackConfigure.setCreateUserName(UserSession.getUser().getAccountName());
            hxSignTrackConfigure.setUpdateUserName(UserSession.getUser().getAccountName());
            hxSignTrackConfigure.setCreateTime(new Date());
            hxSignTrackConfigure.setUpdateTime(new Date());
            count = hxSignTrackConfigureMapper.insert(hxSignTrackConfigure);
        }

        return count;
    }

}
