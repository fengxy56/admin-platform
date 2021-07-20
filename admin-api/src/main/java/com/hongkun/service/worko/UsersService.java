package com.hongkun.service.worko;

import com.hongkun.bean.worko.OaApprovalUsers;
import com.hongkun.bean.worko.Users;
import com.hongkun.bean.worko.UsersQuery;
import com.hongkun.bean.worko.Worko;
import com.hongkun.model.vo.page.PageInfoVO;
import com.hongkun.query.worko.OaApprovalUsersUpdateQuery;
import com.hongkun.query.worko.WorkoQuery;
import com.hongkun.query.worko.WorkoUpdateQuery;

/**
 * @ClassName WorkoService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/11/19 18:45
 */
public interface UsersService {

    //分页查询同事圈用户
    PageInfoVO<Users> getUsersList(UsersQuery usersQuery);

    //账号禁用启用
    int saveOrUpdateOaApprovalUsers(OaApprovalUsersUpdateQuery oaApprovalUsersUpdateQuery);

}
