/*
  版权所有(C) 2018 深圳市雁联计算系统有限公司
  创建: YangHan 2018-12-12
 */
package com.ylink.hibiscus.logistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author YangHan
 * @date 2018-12-12
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ylink.hibiscus.depot.app"))
                .paths(PathSelectors.any())
                .build();
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("银雁金融物流平台-子会员系统-微服务RESTful API")
                //创建人
                .contact(new Contact("YLINK","http://www.ylink.com.cn",""))
                //版本号
                .version("1.0")
                //描述
                .description("银雁金融物流平台-微服务RESTful API")
                .build();

    }
}
