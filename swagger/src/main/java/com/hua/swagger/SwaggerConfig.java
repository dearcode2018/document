package com.hua.swagger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @type SwaggerConfig
 * @description Swagger配置
 * @author qianye.zheng
 */
@Configuration
@EnableWebMvc
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer
{

	/**
	 * 
	 * @description 
     * 这个地方要重新注入一下资源文件，不然不会注入资源的，也没有注入requestHandlerMappping,相当于xml配置的
     *  <!--swagger资源配置-->
     *  <mvc:resources location="classpath:/META-INF/resources/" mapping="swagger-ui.html"/>
     *  <mvc:resources location="classpath:/META-INF/resources/webjars/" mapping="/webjars/**"/>
	 * @param registry
	 * @author qianye.zheng
	 */
     @Override
     public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
         .addResourceLocations("classpath:/META-INF/resources/");
         registry.addResourceHandler("/webjars*")
         .addResourceLocations("classpath:/META-INF/resources/webjars/");
     }
     
     /**
      * 
      * @description 
      * @return
      * @author qianye.zheng
      */
    @Bean
    public Docket customDocket() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
        		.securitySchemes(securitySchemas()).securityContexts(securityContexts());
    }

    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
	private List<SecurityContext> securityContexts()
	{
		List<SecurityContext> list = new ArrayList<SecurityContext>();
		list.add(SecurityContext.builder().securityReferences(defaultAuth())
				.forPaths(PathSelectors.regex("^(?!auth).*$")).build());
		
		return list;
	}

	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	List<SecurityReference> defaultAuth()
	{
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		List<SecurityReference> list =  new ArrayList<SecurityReference>();
		list.add(new SecurityReference("Authorization", authorizationScopes));
		
		return list;
	}
	
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    private List<ApiKey> securitySchemas()
    {
    	List<ApiKey> apiKeys = new ArrayList<ApiKey>();
    	String name = "AuthName";
    	String keyname = "Authorization";
    	String passAs = "123456";
    	apiKeys.add(new ApiKey(name, keyname, passAs));
    	
    	return apiKeys;
    }
    
    /**
     * 
     * @description 
     * @return
     * @author qianye.zheng
     */
    private ApiInfo apiInfo() {
    	// 联系人信息
        Contact contact = new Contact("作者名称xx",  "主页链接xx", "邮箱xxx");
        
        return new ApiInfoBuilder()
        		// 标题
                .title("文档标题-XXAPI接口")
                // 描述
                .description("文档描述-提供给前端访问的API接口")
                .contact(contact)
                // 版本信息
                .version("0.1.0")
                .build();
    }
}