package com.hongkun.mongodb.dao.impl;

import com.hongkun.mongodb.DbName;
import com.hongkun.mongodb.MongoDbClient;
import com.hongkun.mongodb.TableName;
import com.hongkun.mongodb.dao.HxUserUndoStatusDao;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusQuery;
import com.hongkun.query.hx.mongodb.HxUserUndoStatusUpdateQuery;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * @ClassName TWorkReport
 * @Description 这里描述
 * @Author admin
 * @Date 2020/6/10 18:54
 */
@Component
public class HxUserUndoStatusDaoImpl implements HxUserUndoStatusDao {

    /**
     * 根据修改时间的(起时时间和结束时间)   分页查询待办信息
     *
     * @param
     */
    public MongoCursor<Document> findUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery) {

        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.XTDB, TableName.T_USER_UNDO_STATUS);
        BasicDBObject query = new BasicDBObject();

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getMsgId())) {
            query.append("msgId", hxUserUndoStatusQuery.getMsgId());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getAppId())) {
            query.append("appId", hxUserUndoStatusQuery.getAppId());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getTodoStatus())) {
            query.append("todoStatus", hxUserUndoStatusQuery.getTodoStatus());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getAccount())) {
            query.append("account", hxUserUndoStatusQuery.getAccount());
        }


        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getDeleteStatus()) && "false".equals(hxUserUndoStatusQuery.getDeleteStatus())) {
            query.append("delete", false);
        } else if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getDeleteStatus()) && "true".equals(hxUserUndoStatusQuery.getDeleteStatus())) {
            query.append("delete", true);
        }


        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getContent())) {
            Pattern pattern = Pattern.compile("^.*?" + hxUserUndoStatusQuery.getContent() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("content", pattern);
        }

        query.append("updateTime", BasicDBObjectBuilder.start("$gte", hxUserUndoStatusQuery.getStartDate()).add("$lte", hxUserUndoStatusQuery.getEndDate()).get());


        BasicDBObject sort = new BasicDBObject();
        // 1,表示正序； －1,表示倒序
        sort.put("updateTime", -1);
        int start = (hxUserUndoStatusQuery.getPageNo() - 1) * hxUserUndoStatusQuery.getPageSize();
        int limit = hxUserUndoStatusQuery.getPageSize();
        MongoCursor<Document> cursor = collection.find(query).sort(sort).skip(start).limit(limit).iterator();
        return cursor;

    }


    /**
     * 根据修改时间的(起时时间和结束时间)   获取总条数
     */
    public Integer countUndoList(HxUserUndoStatusQuery hxUserUndoStatusQuery) {
        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.XTDB, TableName.T_USER_UNDO_STATUS);
        BasicDBObject query = new BasicDBObject();

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getMsgId())) {
            query.append("msgId", hxUserUndoStatusQuery.getMsgId());
        }
        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getAccount())) {
            query.append("account", hxUserUndoStatusQuery.getAccount());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getTodoStatus())) {
            query.append("todoStatus", hxUserUndoStatusQuery.getTodoStatus());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getAppId())) {
            query.append("appId", hxUserUndoStatusQuery.getAppId());
        }

        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getDeleteStatus()) && "false".equals(hxUserUndoStatusQuery.getDeleteStatus())) {
            query.append("delete", false);
        } else if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getDeleteStatus()) && "true".equals(hxUserUndoStatusQuery.getDeleteStatus())) {
            query.append("delete", true);
        }


        if (StringUtils.isNotBlank(hxUserUndoStatusQuery.getContent())) {
            Pattern pattern = Pattern.compile("^.*?" + hxUserUndoStatusQuery.getContent() + ".*$", Pattern.CASE_INSENSITIVE);
            query.append("content", pattern);
        }
        query.append("updateTime", BasicDBObjectBuilder.start("$gte", hxUserUndoStatusQuery.getStartDate()).add("$lte", hxUserUndoStatusQuery.getEndDate()).get());


        BasicDBObject sort = new BasicDBObject();
        // 1,表示正序； －1,表示倒序
        sort.put("updateTime", -1);
        int count = (int) collection.count(query);
        return count;

    }


    /**
     * 根据主键id把待办修改为已办或者删除已办
     *
     * @param
     */
    public void updateHxUndoStatus(HxUserUndoStatusUpdateQuery hxUserUndoStatusUpdateQuery) {
        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.XTDB, TableName.T_USER_UNDO_STATUS);

        //update 修改数据
        BasicDBObject updateOldSql = new BasicDBObject();
        updateOldSql.append("_id", hxUserUndoStatusUpdateQuery.getId());


        //2.创建更新的文档(需要跟新的字段和值)
        BasicDBObject newObj = new BasicDBObject();
        newObj.append("todoStatus", hxUserUndoStatusUpdateQuery.getTodoStatus());
        newObj.append("delete", hxUserUndoStatusUpdateQuery.isDelete());
        //newObj.append("updateTime",new Date());

        //更新一条数据
        BasicDBObject updateNewOneSql = new BasicDBObject("$set", newObj);

        collection.updateOne(updateOldSql, updateNewOneSql);

    }


    /**
     * 根据用户账号和消息id删除待办数据
     *
     * @param
     */
    public void updateUndoStatusByAccountAndMsyId(String msgId, String account, String todoStatus, boolean delete) {
        MongoCollection<Document> collection = MongoDbClient.instance.getCollection(DbName.XTDB, TableName.T_USER_UNDO_STATUS);

        Document document = new Document();
        document.put("msgId", msgId);
        document.put("account", account);

        //2.创建更新的文档(需要跟新的字段和值)
        BasicDBObject newObj = new BasicDBObject();
        newObj.append("todoStatus", todoStatus);
        newObj.append("delete", delete);
        //newObj.append("updateTime",new Date());

        //更新多条数据
        BasicDBObject updateNewOneSql = new BasicDBObject("$set", newObj);
        collection.updateMany(document, updateNewOneSql);

    }


}
