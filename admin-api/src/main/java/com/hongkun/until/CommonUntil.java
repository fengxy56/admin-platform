package com.hongkun.until;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class CommonUntil {


    public static String getLogReplace(String str) {
        return str.replaceAll("[\r\n]", "");
    }

    public static String getPostParams(Object[] args) {
//        Object[] args = joinPoint.getArgs();
        String params = "";
        List<Object> argsList = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            // 如果参数类型是请求和响应的http，则不需要拼接【这两个参数，使用JSON.toJSONString()转换会抛异常】
            if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse) {
                continue;
            }
            argsList.add(args[i]);
        }

        if (argsList != null && argsList.size() > 0) {
            params = JSON.toJSONString(argsList.get(0), SerializerFeature.WriteMapNullValue);
        }


        return params;

    }


}
