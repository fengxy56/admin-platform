package com.hongkun.until;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class JsonUtil {

    public static String toGson(Object object) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }

    public static <T> T toGsonObject(String jsonObject, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(jsonObject, clazz);
    }

    public static <T> List<T> listFromGson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, new TypeToken<List<T>>() {
        }.getType());
    }

    public static String toJson(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T toObject(String jsonObject, Class<T> clazz) {
        return JSON.parseObject(jsonObject, clazz);
    }

    public static <T> List<T> listFromJson(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }
}
