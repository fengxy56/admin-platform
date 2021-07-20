/**
 *
 */
package com.hongkun.until;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 王胜东--wangsd@t-ark.com
 * @date 2018年8月13日 上午10:03:29
 * @desc
 */
public class LoggerUtil {

    public static final String LOG_TARGET_TYPE = "targetType";
    public static final String LOG_ACTION = "action";
    public static final String LOG_REMARK = "remark";
    public static final String LOG_OPERATE = "operator";

    public static final String UNKNOWN = "unknown";

    public LoggerUtil() {
    }

    /**
     * 获取客户端ip地址
     *
     * @param request
     * @return
     */
    public static String getCliectIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isBlank(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个IP值，第一个为真实IP。
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }
    }
}
