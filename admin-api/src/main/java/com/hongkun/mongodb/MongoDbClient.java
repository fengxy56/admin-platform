package com.hongkun.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;


@SuppressWarnings("deprecation")
public enum MongoDbClient {

    /**
     * 定义一个枚举的元素，它代表此类的一个实例
     */
    instance;
    private static final Logger log = LoggerFactory.getLogger(MongoDbClient.class);

    private static MongoClient mongoClient;


    static {
        try {
            Properties properties = null;
            try {
                String filename = "application-local.yml";
                //获取环境变量

                String active = System.getenv("SPRING_PROFILES_ACTIVE");
                log.info("MongoDbUtil加载文件变量active:" + active);
                if (StringUtils.isNotBlank(active)) {
                    filename = "application-" + active + ".yml";
                }
                log.info("MongoDbUtil加载文件名称filename:" + filename);
                properties = PropertiesLoaderUtils.loadAllProperties(filename);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 从配置文件中获取属性值
            String host = properties.getProperty("mongoDBUrl");
            int port = Integer.parseInt(properties.getProperty("mongoDBPort"));
            log.info("MongoDbUtil加载文件名称host:" + host + ",port:" + port);

            instance.mongoClient = new MongoClient(host, port);
            // db.authenticate(username, passwd)
            // 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
            MongoClientOptions.Builder options = new MongoClientOptions.Builder();
            options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
            options.connectTimeout(15000);// 连接超时，推荐>3000毫秒
            options.maxWaitTime(5000); //
            options.socketTimeout(0);// 套接字超时时间，0无限制
            options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
            options.writeConcern(WriteConcern.SAFE);//
            options.build();

        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------------共用方法---------------------------------------------------

    /**
     * 获取DB实例 - 指定DB
     *
     * @param dbName
     * @return
     */
    public MongoDatabase getDB(String dbName) {
        if (dbName != null && !"".equals(dbName)) {
            MongoDatabase database = mongoClient.getDatabase(dbName);
            return database;
        }
        return null;
    }

    /**
     * 关闭Mongodb
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    public MongoCollection<Document> getCollection(String dbName, String collName) {
        if (null == collName || "".equals(collName)) {
            return null;
        }
        if (null == dbName || "".equals(dbName)) {
            return null;
        }
        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
        return collection;
    }

}