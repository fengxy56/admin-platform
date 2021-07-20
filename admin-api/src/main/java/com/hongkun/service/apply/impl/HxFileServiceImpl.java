package com.hongkun.service.apply.impl;

import com.hongkun.bean.apply.HxFile;
import com.hongkun.mapper.apply.HxFileMapper;
import com.hongkun.service.apply.HxFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @ClassName HxFileServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/23 19:55
 */
@Service
public class HxFileServiceImpl implements HxFileService {

    @Resource
    private HxFileMapper hxFileMapper;


    //保存文件
    public int saveHxFile(HxFile hxFile) {
        hxFile.setCreateTime(new Date());
        hxFile.setUpdateTime(new Date());
        return hxFileMapper.insert(hxFile);
    }

    //查看文件
    public HxFile getHxFileById(String id) {
        return hxFileMapper.selectById(id);
    }


    //获取问卷列表
    public List<HxFile> getHxFileList() {
        return hxFileMapper.selectList(null);
    }

}
