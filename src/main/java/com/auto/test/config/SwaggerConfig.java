package com.auto.test.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger文档
 */
@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
public class SwaggerConfig {
  //  @Bean
//  public Docket createRestApi() {
//    return new Docket(DocumentationType.OAS_30)
//        .apiInfo(apiInfo())
//        .select()
//        .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//        .paths(PathSelectors.any())
//        .build();
//  }
//
//  private ApiInfo apiInfo() {
//    return new ApiInfoBuilder()
//        .title("Swagger3接口文档")
//        .description("更多请咨询服务开发者。")
//        .contact(new Contact("", "http://www.ruiyeclub.cn", "2465915902@qq.com"))
//        .version("1.0")
//        .build();
//  }
  @Bean
  public Docket docket() {
    ParameterBuilder builder = new ParameterBuilder();//TokenFilter.TOKEN_KEY
    builder.parameterType("header").name("")
        .description("header参数")
        .required(false)
        .modelRef(new ModelRef("string")); // 在swagger里显示header
    
    return new Docket(DocumentationType.SWAGGER_2).groupName("自定义接口")
        .apiInfo(new ApiInfoBuilder().title("测试平台自定义接口").description("这些是系统自定义接口")
            .contact(new Contact("自动化测试平台", "http://www.baidu.com", "13048996827@163.com")).version("1.0").build())
//				.enable(true) // 是否开启
        .globalOperationParameters(Lists.newArrayList(builder.build()))
        .select().apis(RequestHandlerSelectors.basePackage("com.auto.test.controller"))// 扫描的路径包
        // 指定路径处理PathSelectors.any()代表所有的路径
        .paths(PathSelectors.any()).paths(Predicates.not(PathSelectors.regex("api/error.*"))).build()// 错误路径不监控
        ;
  }
}
