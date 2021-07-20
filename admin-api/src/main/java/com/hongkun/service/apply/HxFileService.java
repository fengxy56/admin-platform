package com.hongkun.service.apply;

import com.hongkun.bean.apply.HxFile;

import java.util.List;

/**
 * @ClassName HxFileService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/23 19:55
 */
public interface HxFileService {

    //保存文件
    int saveHxFile(HxFile hxFile);

    //查看文件
    HxFile getHxFileById(String id);

    //获取问卷列表
    List<HxFile> getHxFileList();

}
