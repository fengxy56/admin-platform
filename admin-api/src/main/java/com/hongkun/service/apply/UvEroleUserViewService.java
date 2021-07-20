package com.hongkun.service.apply;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hongkun.bean.apply.UvEroleUserView;
import com.hongkun.query.uc.UvERoleUserQuery;

/**
 * @ClassName UvEroleUserViewService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/7/29 17:41
 */
public interface UvEroleUserViewService {


    //分页查询用户账户和角色
    IPage<UvEroleUserView> getUvERoleUserViewList(UvERoleUserQuery uvERoleUserQuery);


}
