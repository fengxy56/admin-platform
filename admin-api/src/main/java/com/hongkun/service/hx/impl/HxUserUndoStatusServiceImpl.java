package com.hongkun.service.hx.impl;

import com.hongkun.model.hx.HxUserUndoStatusModel;
import com.hongkun.mongodb.dao.HxUserUndoStatusDao;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusUpdateQuery;
import com.hongkun.service.hx.HxUserUndoStatusService;
import com.mongodb.client.MongoCursor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @ClassName HxUserUndoStatusServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/21 9:31
 */
@Slf4j
@Service
public class HxUserUndoStatusServiceImpl implements HxUserUndoStatusService {

    @Autowired
    private HxUserUndoStatusDao hxUserUndoStatusDao;

    //获取总条数
    public Integer countUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery) {

        return hxUserUndoStatusDao.countUndoList(hxUserUndoStatusQuery);
    }


    /**
     * 根据修改时间的(起时时间和结束时间)   分页查询待办信息
     *
     * @param
     */
    public List<HxUserUndoStatusModel> findUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery) {

        List<HxUserUndoStatusModel> hxUserUndoStatusModelList = new ArrayList<>();


        long start = System.currentTimeMillis();

        MongoCursor<Document> cursor = hxUserUndoStatusDao.findUndoList(hxUserUndoStatusQuery);

        long end = System.currentTimeMillis();

        log.info("获取鸿信待办：" + hxUserUndoStatusQuery.getAccount() + ",用时：" + (end - start) + "ms");

        while (cursor.hasNext()) {

            Document document = cursor.next();

            HxUserUndoStatusModel hxUserUndoStatusModel = new HxUserUndoStatusModel();

            hxUserUndoStatusModel.setId(document.getString("_id"));
            hxUserUndoStatusModel.setAccount(document.getString("account"));
            hxUserUndoStatusModel.setUserName(document.getString("userName"));
            hxUserUndoStatusModel.setAuthorUser(document.getString("authorUser"));
            hxUserUndoStatusModel.setContent(document.getString("content"));
            hxUserUndoStatusModel.setMsgId(document.getString("msgId"));
            hxUserUndoStatusModel.setAppId(document.getString("appId"));
            hxUserUndoStatusModel.setCreateTime((Date) document.get("createTime"));
            hxUserUndoStatusModel.setUpdateTime((Date) document.get("updateTime"));

            hxUserUndoStatusModel.setTodoStatus(document.getString("todoStatus"));

            hxUserUndoStatusModel.setDelete((boolean) document.get("delete"));
            if (!Objects.isNull(document.get("ifAuthor"))) {
                hxUserUndoStatusModel.setIfAuthor((boolean) document.get("ifAuthor"));
            }
            if (!Objects.isNull(document.get("iscc"))) {
                hxUserUndoStatusModel.setIscc((boolean) document.get("iscc"));
            }
            hxUserUndoStatusModelList.add(hxUserUndoStatusModel);
        }

        return hxUserUndoStatusModelList;

    }


    /**
     * 根据主键id把待办修改为已办或者删除已办
     *
     * @param
     */
    public void updateHxUndoStatus(HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery) {

        hxUserUndoStatusDao.updateHxUndoStatus(hxUserUndoStatusUpdateQuery);
    }

}
