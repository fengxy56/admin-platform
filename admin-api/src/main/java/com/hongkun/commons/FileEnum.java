package com.hongkun.commons;

import lombok.Getter;

/**
 * 文件类型
 */
@Getter
public enum FileEnum {

    FILE_ENUM_IMAGE("img"),
    FILE_ENUM_VIDEO("video"),
    FILE_ENUM_OTHER("other"),;
    private String title;

    private FileEnum(String titie) {//构造方法私有化
        this.title = titie;
    }
}
