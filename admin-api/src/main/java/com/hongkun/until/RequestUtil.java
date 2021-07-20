package com.hongkun.until;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class RequestUtil {

    /**
     * 获取完整的请求url
     *
     * @param request
     * @return
     */
    public static String getRequestUrl(HttpServletRequest request) {
        String url = request.getRequestURL().toString();
        String param = request.getQueryString();

        if (StringUtils.isBlank(param)) {
            String queryString = "";
            Map<String, String[]> params = request.getParameterMap();
            for (String key : params.keySet()) {
                String[] values = params.get(key);
                for (int i = 0; i < values.length; i++) {
                    String value = values[i];
                    queryString += key + "=" + value + "&";
                }
            }
            if (StringUtils.isNotBlank(queryString)) {
                // 去掉最后一个空格
                queryString = queryString.substring(0, queryString.length() - 1);
                url = url + "?" + queryString;
            }
        } else {
            url = url + "?" + param;
        }
        return url;
    }

    /**
     * 根据请求获取 网路协议 + 域名 + 端口
     *
     * @param request
     * @return
     */
    public static String getRequestDomainName(HttpServletRequest request) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String protocol = request.getScheme();
        return protocol + "://" + serverName + ":" + String.valueOf(serverPort);
    }

}
