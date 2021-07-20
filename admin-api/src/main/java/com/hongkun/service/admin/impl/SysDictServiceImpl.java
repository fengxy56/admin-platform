package com.hongkun.service.admin.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.SysDict;
import com.hongkun.commons.ResultEnum;
import com.hongkun.exception.GlobalException;
import com.hongkun.mapper.admin.SysDictMapper;
import com.hongkun.query.SysDictSaveQuery;
import com.hongkun.service.admin.SysDictService;
import com.hongkun.until.CodeGeneratedUtil;

import com.hongkun.model.vo.sys.SysDictTreeVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysDictServiceImpl implements SysDictService {

    @Autowired
    private SysDictMapper sysDictMapper;


    @Override
    public List<SysDict> getSysDictListByParentId(String parentId) {

        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parentId", parentId);
        queryWrapper.eq("Status", 1);//1有效
        queryWrapper.orderByAsc("showOrder");
        List<SysDict> sysDictList = sysDictMapper.selectList(queryWrapper);
        return sysDictList;
    }


    @Override
    public int saveOrUpdateSysDict(SysDictSaveQuery sysDictSaveQuery) {

        int count = 0;

        Date currentDate = new Date();
        SysDict sysDict = new SysDict();
        //第一个是要复制的对下，第二个是复制后的的对象
        BeanUtils.copyProperties(sysDictSaveQuery, sysDict);


        //判断主键id为空,不为空则修改，为空则添加
        if (StringUtils.isNotBlank(sysDictSaveQuery.getId())) {

            if ("-1".equals(sysDictSaveQuery.getParentId())) {
                QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("dictGroup", sysDict.getDictGroup());
                queryWrapper.eq("parentId", -1);
                List<SysDict> list = sysDictMapper.selectList(queryWrapper);
                if (list != null && list.size() > 0 && !list.get(0).getId().equals(sysDictSaveQuery.getId())) {
                    throw new GlobalException(ResultEnum.DICT_NOT_SAME_ERROR);
                }
            }

            sysDict.setId(sysDictSaveQuery.getId());
            sysDict.setUpdateTime(currentDate);
            count = sysDictMapper.updateById(sysDict);

        } else {

            QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dictGroup", sysDict.getDictGroup());
            queryWrapper.eq("dictCode", sysDict.getDictCode());
            queryWrapper.eq("level", sysDict.getLevel());

            List<SysDict> list = sysDictMapper.selectList(queryWrapper);

            if (list != null && list.size() > 0) {
                throw new GlobalException(ResultEnum.DICT_NOT_RESET_ERROR);
            } else {
                sysDict.setId(CodeGeneratedUtil.genUUID());
                sysDict.setCreateTime(currentDate);
                sysDict.setUpdateTime(currentDate);
                count = sysDictMapper.insert(sysDict);
            }

        }

        return count;
    }

    @Override
    public IPage<SysDictTreeVO> getSysDictTreeVO(Integer pageNo, Integer pageSize, String dictGroup, String dicName) {

        Page<SysDict> page = new Page<>(pageNo, pageSize);
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<SysDict>();
        queryWrapper.eq("level", 1);
        if (StringUtils.isNotBlank(dictGroup)) {
            queryWrapper.like("dictGroup", dictGroup);
        }
        if (StringUtils.isNotBlank(dicName)) {
            queryWrapper.like("dictName", dicName);
        }
        page.setAsc("showOrder");
        IPage<SysDict> sysDictPage = sysDictMapper.selectPage(page, queryWrapper);

        List<SysDict> sysDictRecords = sysDictPage.getRecords();
        List<SysDict> sysDictList = new ArrayList<>();

        for (SysDict sysDict : sysDictRecords) {
            QueryWrapper<SysDict> queryGroupWrapper = new QueryWrapper<SysDict>();
            queryGroupWrapper.eq("dictGroup", sysDict.getDictGroup());
            page.setAsc("showOrder");
            List<SysDict> sysDictGroupList = sysDictMapper.selectList(queryGroupWrapper);
            sysDictList.addAll(sysDictGroupList);
        }

        Collections.sort(sysDictList, new Comparator<SysDict>() {
            @Override
            public int compare(SysDict o1, SysDict o2) {
                return o1.getShowOrder() - o2.getShowOrder();
            }
        });

        // 最后的结果
        List<SysDictTreeVO> finalSysDictTreeVOList = new ArrayList<>();

        // 先找到所有的一级菜单
        for (int i = 0; i < sysDictList.size(); i++) {
            // 一级菜单parentId为-1
            //if ("-1".equals(SysDictList.get(i).getParentId())) {
            SysDict sysDict = sysDictList.get(i);
            if ("-1".equals(sysDictList.get(i).getParentId())) {
                SysDictTreeVO sysDictTreeVO = new SysDictTreeVO();
                //第一个是要复制的对下，第二个是复制后的的对象
                BeanUtils.copyProperties(sysDict, sysDictTreeVO);

                finalSysDictTreeVOList.add(sysDictTreeVO);
            }
        }

        // 为一级菜单设置子菜单，getChild是递归调用的
        for (SysDictTreeVO sysDictTreeVO : finalSysDictTreeVOList) {
            sysDictTreeVO.setChildren(this.getChild(sysDictTreeVO.getId(), sysDictList));
        }

        IPage<SysDictTreeVO> sysDictTreeVOPage = new Page<>();
        ;
        BeanUtils.copyProperties(sysDictPage, sysDictTreeVOPage);
        sysDictTreeVOPage.setRecords(finalSysDictTreeVOList);
        return sysDictTreeVOPage;
    }

    /**
     * 递归查找子菜单
     *
     * @param id          当前菜单id
     * @param sysDictList 要查找的列表
     * @return
     */
    private List<SysDictTreeVO> getChild(String id, List<SysDict> sysDictList) {
        // 子菜单
        List<SysDictTreeVO> childList = new ArrayList<>();

        for (SysDict sysDict : sysDictList) {
            // 遍历所有节点，将父菜单id与传过来的id比较
            if (StringUtils.isNotBlank(sysDict.getParentId())) {
                if (sysDict.getParentId().equals(id)) {

                    SysDictTreeVO sysDictTreeVO = new SysDictTreeVO();
                    //第一个是要复制的对下，第二个是复制后的的对象
                    BeanUtils.copyProperties(sysDict, sysDictTreeVO);
                    childList.add(sysDictTreeVO);
                }
            }
        }
        // 把子菜单的子菜单再循环一遍
        for (SysDictTreeVO sysDict : childList) {// 没有url子菜单还有子菜单
            // 递归
            SysDictTreeVO sysDictTreeVO = new SysDictTreeVO();
            //第一个是要复制的对下，第二个是复制后的的对象
            BeanUtils.copyProperties(sysDict, sysDictTreeVO);
            sysDict.setChildren(this.getChild(sysDict.getId(), sysDictList));
        }

        // 递归退出条件
        if (childList.size() == 0) {
            return null;
        }
        return childList;
    }


}
