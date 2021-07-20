package com.hongkun.service.hxopensys;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.hx.TBdPerson;
import com.hongkun.query.hx.TBdPersonQuery;
import com.hongkun.query.hx.TBdPersonUpdateQuery;

/**
 * @ClassName TBdPersonService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/5/27 7:40
 */
public interface TBdPersonService {

    //分页查询用户信息
    IPage<TBdPerson> getTBdPersonList(TBdPersonQuery tBdPersonQuery);

    //修改用户最后时间
    void updateTBdPersonLastUpdateTime(TBdPersonUpdateQuery tBdPersonUpdateQuery);

}
