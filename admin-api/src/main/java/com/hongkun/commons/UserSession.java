package com.hongkun.commons;


import com.hongkun.model.vo.uc.UvEroleUserVO;

/**
 * @author chiaxuhong
 * @date 2019-08-14
 */
public class UserSession {
    private static final ThreadLocal<UvEroleUserVO> LOCAL_USER = new ThreadLocal<>();

    public static void setUser(UvEroleUserVO user) {
        LOCAL_USER.set(user);
    }

    public static void remove() {
        LOCAL_USER.remove();
    }

    public static UvEroleUserVO getUser() {
        return LOCAL_USER.get();
    }
}

