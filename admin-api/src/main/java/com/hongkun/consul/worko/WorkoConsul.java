package com.hongkun.consul.worko;

import com.hongkun.bean.worko.OaApprovalUsers;
import com.hongkun.bean.worko.UsersQuery;
import com.hongkun.query.worko.OaApprovalUsersUpdateQuery;
import com.hongkun.query.worko.WorkoQuery;
import com.hongkun.query.worko.WorkoUpdateQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @ClassName UvUserViewService
 * @Description 这里描述
 * @Author admin
 * @Date 2020/9/27 14:04
 */

@FeignClient(value = "hx-worko-java")
public interface WorkoConsul {


    //分页查询同事圈内容
    @PostMapping("/hx-worko-java/worko/getWorkoList")
    String getWorkoList(WorkoQuery workoQuery);


    //修改同事圈是否置顶或者删除
    @PostMapping("/hx-worko-java/worko/updateWorko")
    String updateWorko(WorkoUpdateQuery workoUpdateQuery);


    //分页查询同事圈用户
    @PostMapping("/hx-worko-java/users/getUsers")
    String getUsers(UsersQuery usersQuery);

    //分页查询同事圈用户
    @PostMapping("/hx-worko-java/users/saveOrUpdateOaApprovalUsers")
    String saveOrUpdateOaApprovalUsers(OaApprovalUsersUpdateQuery oaApprovalUsersUpdateQuery);

}
