package com.hongkun.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author krystal
 * @date 2018/10/30
 */
@Configuration
/*@MapperScan("com.dm.krystal")*/ /*  KrystalApiApplication在启动文件上写了，这里不加*/
public class MyBatisPlusConfig {
    /**
     * mybatis-plus分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }
}
