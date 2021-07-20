package com.hongkun.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: Swagger配置类
 * @Author: PCL
 * @CreateDate: 2018/7/18 14:52
 * @UpdateUser: PCL
 * @UpdateDate: 2018/7/18 14:52
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private boolean enableSwagger;

    @Bean
    public Docket createRestApi() {

        List<Parameter> pars = new ArrayList<Parameter>();

//        ParameterBuilder ticketPar = new ParameterBuilder();
//        ticketPar.name("companyEntitiesId").defaultValue("8D6908B8-A244-46E3-B730-994B47852B77").description("实体公司id")
//                .modelRef(new ModelRef("string")).parameterType("header")
//                .required(false).build(); //header中的ticket参数非必填，传空也可以

        ParameterBuilder ticketPar2 = new ParameterBuilder();
        ticketPar2.name("token").defaultValue("FDFE95A38152F5826A9C05B2BA5AF7889AFA61F3E2D1ECB19D2EA0C94C1CD98E").description("用户token")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build(); //header中的ticket参数非必填，传空也可以

//        pars.add(ticketPar.build());//根据每个方法名也知道当前方法在设置什么参数
        pars.add(ticketPar2.build());//根据每个方法名也知道当前方法在设置什么参数


        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("鸿客集团统一接口管理V1.0")
                .enable(enableSwagger)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build()
                .globalOperationParameters(pars);

    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("鸿客集团统一接口管理")
                .description("系统配置")
                .version("1.0")
                .build();
    }

    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo2() {
        return new ApiInfoBuilder()
                .title("鸿客集团统一接口管理")
                .description("鸿坤集团")
                .version("1.0")
                .build();
    }

}
