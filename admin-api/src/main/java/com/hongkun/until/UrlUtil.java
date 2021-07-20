package com.hongkun.until;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chaixuhong
 * @apiNote 请求url解析工具类
 * @date 2019-08-12
 */
public class UrlUtil {
    /**
     * 获取请求完整url
     */
    public static String getRequestUrl(HttpServletRequest request) {
        StringBuffer baseUrlBuffer = request.getRequestURL();
        Map<String, String[]> requestParams = request.getParameterMap();
        if (requestParams.size() > 0) {
            baseUrlBuffer.append("?");
            for (String key : requestParams.keySet()) {
                String[] values = requestParams.get(key);
                for (String value : values) {
                    baseUrlBuffer.append(key);
                    baseUrlBuffer.append("=");
                    baseUrlBuffer.append(value);
                }
                baseUrlBuffer.append("&");
            }
        }
        if (baseUrlBuffer.lastIndexOf("&") > -1) {
            baseUrlBuffer.deleteCharAt(baseUrlBuffer.lastIndexOf("&"));
        }
        return baseUrlBuffer.toString();
    }


    /**
     * 获取请求参数中所有的信息
     *
     * @param request
     * @return
     */
    public static Map<String, String> getAllRequestParam(
            final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                // 在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
                if (res.get(en) == null || "".equals(res.get(en))) {
                    // System.out.println("======为空的字段名===="+en);
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
