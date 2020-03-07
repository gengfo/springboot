package com.javalsj.blog.autoconfigure.swagger2;

import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description 使用Swagger2自动构建 Restful API文档功能配置类
 * @author WANGJIHONG
 * @date 2018年2月12日 下午9:03:42 
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 通过@EnableSwagger2开启swagger2，swagger通过注解表明该接口会生成文档，包括接口名、请求方法、参数、返回信息的等等。
 *		@Api：修饰整个类，描述Controller的作用
 *		@ApiOperation：描述一个类的一个方法，或者说一个接口
 *		@ApiParam：单个参数描述
 *		@ApiModel：用对象来接收参数
 *		@ApiProperty：用对象接收参数时，描述对象的一个字段
 *		@ApiResponse：HTTP响应其中1个描述
 *		@ApiResponses：HTTP响应整体描述
 *		@ApiIgnore：使用该注解忽略这个API
 *		@ApiError ：发生错误返回的信息
 *		@ApiParamImplicitL：一个请求参数
 *		@ApiParamsImplicit 多个请求参数
 *		启动工程，访问：https://localhost:8443/blog/swagger-ui.html，就看到swagger-ui:
 */
@Configuration
@EnableSwagger2
public class Swagger2Configuration {
	
	/**
	 * apis()指定扫描的包会生成文档。
	 */
	private static final String API_SCAN_BASEPACKAGE = "com.javalsj.blog";

	private final Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
		@Override
        public boolean apply(RequestHandler input) {
        	Predicate<RequestHandler> predicate_basePackage = RequestHandlerSelectors.basePackage(API_SCAN_BASEPACKAGE);
        	if (!predicate_basePackage.apply(input)) {
        		// 指定扫描包
				return false;
			}
			Class<?> declaringClass = input.getReturnType().getClass();
            if (declaringClass == BasicErrorController.class) {
            	// 指定排除的类
                return false;
            }
            if(declaringClass.isAnnotationPresent(RestController.class)) {
            	// 指定被注解的类
            	return true;
            }
            if(input.isAnnotatedWith(ResponseBody.class)) {
            	// 指定被注解的方法
            	return true;
            }
            return false;
        }
    };
	
	/**
	 * 支持指定扫描包，指定注解生产API文档
	 * Swagger会默认把所有Controller中的RequestMapping方法都生成API出来，
	 * 实际上我们一般只需要标准接口的（像返回页面的那种Controller方法我们并不需要），
	 * 所有你可以按下面的方法来设定要生成API的方法的要求。 
	 * 如下我针对RestController注解的类和ResponseBody注解的方法才生成Swaager的API，并且排除了特定的类。
	 * @return Docket API标签摘要信息
	 */
	@Bean
	public Docket buildDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				// 分组
				.groupName("blog") 
				.apiInfo(buildApiInfo()) 
				.select()
				.apis(predicate)
				.paths(PathSelectors.any())
				.build();
	}

	/**
	 * apiInfo()配置一些基本的信息
	 */
	private ApiInfo buildApiInfo() {
		return new ApiInfoBuilder()
				// 大标题
				.title("博客系统接口API")
				// 详细描述
				.description("博客系统接口API").termsOfServiceUrl("http://www.javalsj.com/blog")
				// 作者
				.contact(new Contact("WANGJIHONG", "http://www.javalsj.com/blog", "javalsj@163.com"))
				// 版本
				.version("1.0").build();
	}
	
}
