package com.hongkun.mongodb.dao;

import com.hongkun.query.hx.mongodb.HxUserUndoStatusQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusUpdateQuery;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 * @ClassName TWorkReport
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/10 18:54
 */
public interface HxUserUndoStatusDao {

    /**
     * 根据修改时间的(起时时间和结束时间)   分页查询待办信息
     *
     * @param
     */
    MongoCursor<Document> findUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery);


    //获取总条数
    Integer countUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery);


    /**
     * 根据主键id把待办修改为已办或者删除已办
     *
     * @param
     */
    void updateHxUndoStatus(HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery);


    //根据消息id和账号修改待办
    void updateUndoStatusByAccountAndMsyId(String msgId, String account, String todoStatus, boolean delete);


}
