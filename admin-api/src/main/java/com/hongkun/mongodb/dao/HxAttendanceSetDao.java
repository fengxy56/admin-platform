package com.hongkun.mongodb.dao;

import com.hongkun.query.hx.mongodb.HxAttendanceSetQuery;
import com.hongkun.query.hx.mongodb.HxAttendanceSetUpdateQuery;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

/**
 * @ClassName HxAttendanceSet
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 13:53
 */
public interface HxAttendanceSetDao {

    /**
     * 分页查询签到新表
     *
     * @param
     */
    MongoCursor<Document> getHxAttendanceSetList(HxAttendanceSetQuery hxAttendanceSetQuery);


    //获取签到信息总数量
    Integer countHxAttendanceSet(HxAttendanceSetQuery hxAttendanceSetQuery);


    MongoCursor<Document> getHxAttendanceSetById(String id);

    void updateHxAttendanceSetById(HxAttendanceSetUpdateQuery hxAttendanceSetUpdateQuery);


}
