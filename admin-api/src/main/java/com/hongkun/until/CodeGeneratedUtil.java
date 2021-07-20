package com.hongkun.until;

import java.util.UUID;

/**
 * @author chaixuhong
 * @date 2019-09-01
 */
public class CodeGeneratedUtil {

    /**
     * 生成UUID
     *
     * @return
     */
    public static String genUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
