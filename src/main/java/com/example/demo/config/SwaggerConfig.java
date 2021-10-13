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

    /**
     * 测试的接口
     */
    @Bean
    public Docket testApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("test", "我的一些测试接口"))
                .groupName("我的test分组")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller.test"))                //当前包路径，控制器类包
                .build();
    }

    @Bean
    public Docket otherApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo("other", "一些其他接口"))
                .groupName("另外的分组")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.demo.controller.other"))                //当前包路径，控制器类包
                .build();
    }

    private ApiInfo apiInfo(String title, String description) {
        return new ApiInfoBuilder()
                .title(title)
                .description(description)
                .build();
    }
}
