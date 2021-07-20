package com.hongkun.service.hxlightapps.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.HxPollImage;
import com.hongkun.mapper.apply.HxPollImageMapper;
import com.hongkun.query.hx.HxPollImageSaveOrUpdateQuery;
import com.hongkun.query.hx.HxPollImageVO;
import com.hongkun.service.hxlightapps.HxPollImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName HxPollImageServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 17:37
 */
@Service
public class HxPollImageServiceImpl implements HxPollImageService {

    @Autowired
    private HxPollImageMapper hxPollImageMapper;


    //查询轮播图
    public List<HxPollImageVO> getHxPollImageList() {

        List<HxPollImageVO> newList = new ArrayList<>();
        QueryWrapper<HxPollImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");
        List<HxPollImage> list = hxPollImageMapper.selectList(queryWrapper);
        if (list != null && list.size() > 0) {
            for (HxPollImage hxPollImage : list) {
                HxPollImageVO hxPollImageVO = new HxPollImageVO();

                hxPollImageVO.setId(hxPollImage.getId());
                hxPollImageVO.setImageUrlPath(hxPollImage.getPollImage());
                hxPollImageVO.setGoToUrl(hxPollImage.getImageUrl());
                hxPollImageVO.setShowDescribe(hxPollImage.getUrlTitle());
                hxPollImageVO.setShowOrder(hxPollImage.getSort());
                hxPollImageVO.setType(hxPollImage.getType());
                newList.add(hxPollImageVO);
            }

        }
        return newList;
    }

    //获取最大id
    public int getMaxId() {
        int id = 0;
        Page<HxPollImage> page = new Page<>(1, 1);
        QueryWrapper<HxPollImage> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        IPage<HxPollImage> pageList = hxPollImageMapper.selectPage(page, queryWrapper);
        List<HxPollImage> list = pageList.getRecords();
        if (list != null && list.size() > 0) {
            id = list.get(0).getId();
        }

        return id;
    }

    //保存修改轮播图
    public void saveOrUpdateHxPollImage(HxPollImageSaveOrUpdateQuery hxPollImageSaveOrUpdateQuery) {

        List<String> deleteList = hxPollImageSaveOrUpdateQuery.getDeletedHxPollImageList();
        if (deleteList != null && deleteList.size() > 0) {
            for (String deleteStr : deleteList) {
                hxPollImageMapper.deleteById(deleteStr);
            }
        }

        List<HxPollImageVO> hxPollImageVOList = hxPollImageSaveOrUpdateQuery.getHxPollImageList();
        if (hxPollImageVOList != null && hxPollImageVOList.size() > 0) {

            for (HxPollImageVO hxPollImageVO : hxPollImageVOList) {

                HxPollImage hxPollImage = new HxPollImage();

                hxPollImage.setPollImage(hxPollImageVO.getImageUrlPath());//url
                hxPollImage.setSort(hxPollImageVO.getShowOrder());//排序
                hxPollImage.setUrlTitle(hxPollImageVO.getShowDescribe());//跳转地址标题
                hxPollImage.setType(hxPollImageVO.getType());//类型
                hxPollImage.setImageUrl(hxPollImageVO.getGoToUrl());//跳转地址

                if (hxPollImageVO.getId() != null) {
                    hxPollImage.setId(hxPollImageVO.getId());
                    hxPollImageMapper.updateById(hxPollImage);
                } else {
                    hxPollImage.setId(this.getMaxId() + 1);
                    hxPollImageMapper.insert(hxPollImage);
                }

            }
        }


    }

}
