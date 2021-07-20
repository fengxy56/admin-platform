package com.hongkun.service.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.SysDict;
import com.hongkun.query.SysDictSaveQuery;
import com.hongkun.model.vo.sys.SysDictTreeVO;

import java.util.List;


public interface SysDictService {


    //分页搜索查询字典树状
    IPage<SysDictTreeVO> getSysDictTreeVO(Integer pageNo, Integer pageSize, String dicGroup, String dicName);

    //保存修改字典项内容
    int saveOrUpdateSysDict(SysDictSaveQuery xDictionarySaveQuery);

    //根据父级id查询字典集合
    List<SysDict> getSysDictListByParentId(String parentId);


}
