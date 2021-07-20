package com.hongkun.service.apply.impl;

import com.hongkun.bean.common.LogAdminApi;
import com.hongkun.mapper.apply.LogAdminApiMapper;
import com.hongkun.service.apply.LogAdminApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName LogAdminApiServiceImpl
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/29 10:21
 */
@Service
public class LogAdminApiServiceImpl implements LogAdminApiService {

    @Autowired
    private LogAdminApiMapper logAdminApiMapper;

    public void saveLogAdminApi(LogAdminApi logAdminApi) {
        logAdminApiMapper.insert(logAdminApi);
    }


}
