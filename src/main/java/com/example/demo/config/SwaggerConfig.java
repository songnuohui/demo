package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author SongNuoHui
 * @date 2021/9/2 10:05
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket creatRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("my test")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller"))                //当前包路径，控制器类包
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("我的demo")
                .version("1.0")                //.contact(new Contact("song","http://localhost:8888","2758324196@qq.com"))
                .description("Api描述")
                .build();
    }
}
