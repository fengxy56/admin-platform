package com.hongkun.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: MongoDB数据库链接配置类
 * @Author: PCL
 * @CreateDate: 2018/9/30 16:16
 * @UpdateUser: PCL
 * @UpdateDate: 2018/9/30 16:16
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Component
@Data
public class ConfigInfo {


    /**
     * 短信发送url
     */
    @Value("${config.hx.orgInfoId}")
    private String hxOrgInfoId;

    /**
     * 同事圈url
     */
    @Value("${config.hx.tsqUrl}")
    private String tsqUrl;


    /**
     * 文件路径
     */
    @Value("${config.hx.filePath}")
    private String filePath;

//
//    /**
//     * 短信发送密码
//     */
//    @Value("${config.smsPwd}")
//    private String smsPwd;


}
