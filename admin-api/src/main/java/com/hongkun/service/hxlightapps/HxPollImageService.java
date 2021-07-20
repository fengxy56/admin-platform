package com.hongkun.service.hxlightapps;

import com.hongkun.query.hx.HxPollImageSaveOrUpdateQuery;
import com.hongkun.query.hx.HxPollImageVO;

import java.util.List;

/**
 * @ClassName HxPollImageService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 17:36
 */
public interface HxPollImageService {

    //获取轮播图
    List<HxPollImageVO> getHxPollImageList();

    //保存修改轮播图
    void saveOrUpdateHxPollImage(HxPollImageSaveOrUpdateQuery hxPollImageSaveOrUpdateQuery);


}
