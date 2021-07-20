package com.hongkun.commons.cache;


import com.hongkun.commons.Constants;
import com.hongkun.until.JsonUtil;
import com.hongkun.until.RedisUtil;
import com.hongkun.model.vo.uc.UvEroleUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class TokenCache {

    private final static String TOKEN_PREFIX = Constants.CACHE_PREFIX + ".token:%s";


    @Value("${cookie.maxAge}")
    private Integer maxAge;


    @Autowired
    private RedisUtil redisUtil;

    private String genKey(String tokenKey) {
        return String.format(TOKEN_PREFIX, tokenKey);
    }

    public void setToken(String tokenKey, UvEroleUserVO user) {
        redisUtil.setEx(genKey(tokenKey), JsonUtil.toJson(user),
                maxAge, TimeUnit.SECONDS);
    }

    public UvEroleUserVO getToken(String token) {
        String tokenKey = genKey(token);
        String result = redisUtil.get(tokenKey);
        Long expireDay = redisUtil.getExpire(tokenKey, TimeUnit.HOURS);
        if (expireDay < 1000) {
            redisUtil.expire(tokenKey, maxAge, TimeUnit.SECONDS);
        }
        return JsonUtil.toObject(result, UvEroleUserVO.class);
    }

    public void rename(String oldToken, String newToken) {
        redisUtil.rename(genKey(oldToken), genKey(newToken));
    }

    public void delToken(String token) {
        redisUtil.delete(genKey(token));
    }
}
