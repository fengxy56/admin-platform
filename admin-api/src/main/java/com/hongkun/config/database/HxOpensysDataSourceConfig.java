package com.hongkun.config.database;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @Description: 多数据：费控数据源配置类
 * @Author: PCL
 * @CreateDate: 2018/7/19 14:14
 * @UpdateUser: PCL
 * @UpdateDate: 2018/7/19 14:14
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.hongkun.mapper.hxopensys"}, sqlSessionTemplateRef = "hxopensysSqlSessionTemplate")
public class HxOpensysDataSourceConfig {

    @Bean(name = "hxopensysDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.druid.hxopensys")
    public DataSource oracleDataSource() {
        return new DruidDataSource();
    }

    @Bean(name = "hxopensysSqlSessionFactory")
    public MybatisSqlSessionFactoryBean oracleSqlSessionFactory(@Qualifier("hxopensysDataSource") DataSource dataSource) throws Exception {

        MybatisSqlSessionFactoryBean mybatisPlus = new MybatisSqlSessionFactoryBean();
        mybatisPlus.setDataSource(dataSource);
        mybatisPlus.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mappers/*.xml"));
//        mybatisPlus.getConfiguration().setMapUnderscoreToCamelCase(false);//驼峰转换


        MybatisConfiguration mc = new MybatisConfiguration();
        mc.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
        mc.setMapUnderscoreToCamelCase(false);// 数据库和java都是驼峰，就不需要
        mybatisPlus.setConfiguration(mc);

        //手动设置session工厂时，需要手动添加分页插件
        mybatisPlus.setPlugins(new Interceptor[]{new PaginationInterceptor().setDialectType(DbType.MYSQL.getDb())});

        return mybatisPlus;
    }

    @Bean(name = "hxopensysTransactionManager")
    public DataSourceTransactionManager oracleTransactionManager(@Qualifier("hxopensysDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "hxopensysSqlSessionTemplate")
    public SqlSessionTemplate oracleSqlSessionTemplate(@Qualifier("hxopensysSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
