package com.hongkun.service.apply.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hongkun.bean.apply.UvEroleUserView;
import com.hongkun.mapper.apply.UvEroleUserViewMapper;
import com.hongkun.query.uc.UvERoleUserQuery;
import com.hongkun.service.apply.UvEroleUserViewService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName UvEroleUserViewServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/29 17:41
 */
@Slf4j
@Service
public class UvEroleUserViewServiceImpl implements UvEroleUserViewService {


    @Resource
    private UvEroleUserViewMapper uvEroleUserViewMapper;


    @Override
    public IPage<UvEroleUserView> getUvERoleUserViewList(UvERoleUserQuery uvERoleUserQuery) {

        Page<UvEroleUserView> page = new Page<>(uvERoleUserQuery.getPageNo(), uvERoleUserQuery.getPageSize());
        QueryWrapper<UvEroleUserView> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("statusType", 0);
        //账户
        if (StringUtils.isNotBlank(uvERoleUserQuery.getAccountName())) {
            queryWrapper.like("accountName", uvERoleUserQuery.getAccountName());
        }
        //姓名
        if (StringUtils.isNotBlank(uvERoleUserQuery.getChsName())) {
            queryWrapper.like("chsName", uvERoleUserQuery.getChsName());
        }
        //标准角色id
        if (StringUtils.isNotBlank(uvERoleUserQuery.getBRoleId())) {
            queryWrapper.like("bRoleId", uvERoleUserQuery.getBRoleId());
        }
        //标准角色名称
        if (StringUtils.isNotBlank(uvERoleUserQuery.getBRoleName())) {
            queryWrapper.like("bRoleName", uvERoleUserQuery.getBRoleName());
        }
        queryWrapper.eq("statusType", 0);//0启用
        IPage<UvEroleUserView> uvERoleUserIPage = uvEroleUserViewMapper.selectPage(page, queryWrapper);

        log.info("总条数" + uvERoleUserIPage.getTotal() + ",总页数" + uvERoleUserIPage.getPages());


        return uvERoleUserIPage;

    }


}
