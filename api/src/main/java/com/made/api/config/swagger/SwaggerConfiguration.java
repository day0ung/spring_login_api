package com.made.api.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket SwaggerAPI() {

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo()) // API Doc 및 작성자 정보 매핑
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any()) //controller package 전부
                //.paths(pathSelecors.ant("/v1/**")) //controller 패키지 내 v1만 지정해서 할수도 있다.
                .build()
                .useDefaultResponseMessages(false); // 기본 세팅값인 200, 401, 402..
    }

    private ApiInfo swaggerInfo() {

        return new ApiInfoBuilder().title("Spring API Documentation")
                .description("API infomation")
                .license("apitest")
                .licenseUrl("apitest.com")
                .version("1")
                .build();
    }
}
