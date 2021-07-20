package com.hongkun.until;

import com.hongkun.commons.Constants;
import com.hongkun.commons.FileEnum;


import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName WorkUntil
 * @Description 凭借
 * @Author admin
 * @Date 2020/4/7 16:53
 */
public class WorkUntil {


    /**
     * 拼接资源地址
     *
     * @param request  网络请求
     * @param fileId   文件id
     * @param fileType 文件类型枚举
     * @param isThum   "1"：缩略图，"0"：原图
     * @return
     */
    public static String getFileUrl(HttpServletRequest request, String fileId, FileEnum fileType, String isThum) {
        String serverName = RequestUtil.getRequestDomainName(request);
        return getFileUrl(serverName, fileId, fileType, isThum);
    }

    /**
     * 拼接资源地址
     *
     * @param serverName protocol + domain + port
     * @param fileId     文件id
     * @param fileType   文件类型枚举
     * @param isThum     "1"：缩略图，"0"：原图
     * @return
     */
    public static String getFileUrl(String serverName, String fileId, FileEnum fileType, String isThum) {
        String filePath = Constants.FILE_PATH;      // 路径部分
        return serverName + filePath + "fileId=" + fileId + "&fileType=" + fileType.getTitle() + "&imgType=" + isThum;
    }

}
