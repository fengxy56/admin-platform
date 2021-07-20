package com.hongkun.mongodb.dao.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hongkun.model.hx.HxWifiInfoModel;
import com.hongkun.mongodb.DbName;
import com.hongkun.mongodb.MongoDbClient;
import com.hongkun.mongodb.TableName;
import com.hongkun.mongodb.dao.HxAttendanceSetDao;
import com.hongkun.query.hx.mongodb.HxAttendanceSetQuery;
import com.hongkun.query.hx.mongodb.HxAttendanceSetUpdateQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @ClassName HxAttendanceSetDaoImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/25 13:54
 */
@Component
public class HxAttendanceSetDaoImpl implements HxAttendanceSetDao {


    /**
     * 分页查询签到新表
     *
     * @param
     */
    public MongoCursor<Document> getHxAttendanceSetList(HxAttendanceSetQuery hxAttendanceSetQuery) {

        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.ATTDEV, TableName.T_ATTENDANCESET);
        BasicDBObject query = new BasicDBObject();

        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getId())) {
            query.append("_id", hxAttendanceSetQuery.getId());
        }

        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getPositionName())) {
            Pattern pattern = Pattern.compile("^.*?" + hxAttendanceSetQuery.getPositionName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("position.positionName", pattern);
        }

        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getAddress())) {
            Pattern pattern = Pattern.compile("^.*?" + hxAttendanceSetQuery.getAddress() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("position.address", pattern);
        }

        query.append("isDelete", false);


        BasicDBObject sort = new BasicDBObject();
        // 1,表示正序； －1,表示倒序
        sort.put("updateTime", -1);
        int start = (hxAttendanceSetQuery.getPageNo() - 1) * hxAttendanceSetQuery.getPageSize();
        int limit = hxAttendanceSetQuery.getPageSize();
        MongoCursor<Document> cursor = collection.find(query).sort(sort).skip(start).limit(limit).iterator();
        return cursor;

    }


    //获取签到信息总数量
    public Integer countHxAttendanceSet(HxAttendanceSetQuery hxAttendanceSetQuery) {

        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.ATTDEV, TableName.T_ATTENDANCESET);
        BasicDBObject query = new BasicDBObject();
        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getId())) {
            query.append("_id", hxAttendanceSetQuery.getId());
        }
        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getPositionName())) {
            Pattern pattern = Pattern.compile("^.*?" + hxAttendanceSetQuery.getPositionName() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("position.positionName", pattern);
        }
        if (StringUtils.isNotBlank(hxAttendanceSetQuery.getAddress())) {
            Pattern pattern = Pattern.compile("^.*?" + hxAttendanceSetQuery.getAddress() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("position.address", pattern);
        }
        query.append("isDelete", false);


        BasicDBObject sort = new BasicDBObject();
        // 1,表示正序； －1,表示倒序
        sort.put("updateTime", -1);
        int start = (hxAttendanceSetQuery.getPageNo() - 1) * hxAttendanceSetQuery.getPageSize();
        int limit = hxAttendanceSetQuery.getPageSize();
        int count = (int) collection.count(query);
        return count;

    }


    /**
     * 根据主键id查询wifi信息
     *
     * @param
     */
    public MongoCursor<Document> getHxAttendanceSetById(String id) {

        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.ATTDEV, TableName.T_ATTENDANCESET);
        BasicDBObject query = new BasicDBObject();
        query.append("_id", id);
        query.append("isDelete", false);
        BasicDBObject sort = new BasicDBObject();
        // 1,表示正序； －1,表示倒序
        sort.put("updateTime", -1);

        MongoCursor<Document> cursor = collection.find(query).sort(sort).iterator();
        return cursor;

    }


    /**
     * 根据主键id查询wifi信息
     *
     * @param
     */
    public void updateHxAttendanceSetById(HxAttendanceSetUpdateQuery hxAttendanceSetUpdateQuery) {

        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.ATTDEV, TableName.T_ATTENDANCESET);


        //update 修改数据
        BasicDBObject updateOldSql = new BasicDBObject();
        updateOldSql.append("_id", hxAttendanceSetUpdateQuery.getId());
        updateOldSql.append("isDelete", false);

        //2.创建更新的文档(需要跟新的字段和值)
        BasicDBObject newObj = new BasicDBObject();


        List<HxWifiInfoModel> list = hxAttendanceSetUpdateQuery.getWifiInoList();
        if (list != null && list.size() > 0) {
            JSONArray jsonArray = new JSONArray();

            for (HxWifiInfoModel hxWifiInfoModel : list) {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ssid", hxWifiInfoModel.getSsid());
                jsonObject.put("bssid", hxWifiInfoModel.getBssid());
                jsonObject.put("infoAddress", hxWifiInfoModel.getInfoAddress());

                jsonObject.put("type", hxWifiInfoModel.getType());
                jsonObject.put("hasNotify", hxWifiInfoModel.isHasNotify());
                jsonArray.add(jsonObject);
            }

            newObj.append("wifis", jsonArray);

        }

        newObj.append("updateTime", new Date());


        //更新一条数据
        BasicDBObject updateNewOneSql = new BasicDBObject("$set", newObj);

        collection.updateOne(updateOldSql, updateNewOneSql);

    }

}
