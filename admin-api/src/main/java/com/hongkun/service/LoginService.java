package com.hongkun.service;

import com.hongkun.model.vo.LoginUserVO;
import com.hongkun.query.LoginUserQuery;

/**
 * @ClassName LoginService
 * @Description 这里描述
 * @Author admin
 * @Date 2021/4/9 10:19
 */
public interface LoginService {


    //获取用户登录信息
    LoginUserVO getLoginUserByAccount(LoginUserQuery loginUserQuery);

    LoginUserVO getSsoLoginUserByAccount(String account);


}
