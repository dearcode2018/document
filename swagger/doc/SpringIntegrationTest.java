/**
 * 描述: 
 * SpringIntegrationTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test.mvc;

// 静态导入
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.hua.controller.sys.UserController;
import com.hua.entity.User;
import com.hua.test.BaseControllerTest;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * SpringIntegrationTest
 */
/*
 * 
 * @SpringJUnit4ClassRunner 运行器负责拉起 spring 环境
 * @ContextConfiguration 指定 spring配置文件，若不指定，则使用默认配置.
 */
// for Junit 4.x
@RunWith(SpringJUnit4ClassRunner.class)

@WebAppConfiguration(value = "src/main/webapp")
/*@ContextHierarchy({
	@ContextConfiguration(locations = {"classpath:conf/xml/spring-bean.xml", "classpath:conf/xml/spring-config.xml", 
			"classpath:conf/xml/spring-mvc.xml", "classpath:conf/xml/spring-service.xml"})
})*/
@ContextConfiguration(locations = {"classpath:conf/xml/spring-bean.xml", "classpath:conf/xml/spring-config.xml", 
		"classpath:conf/xml/spring-mvc.xml", "classpath:conf/xml/spring-service.xml"})

//@ContextConfiguration(locations = {"classpath:conf/xml/applicationContext.xml"})

/*@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "src/main/webapp")  
@ContextHierarchy({  
      @ContextConfiguration(name = "parent", classes = AppConfig.class),  
      @ContextConfiguration(name = "child", classes = MvcConfig.class)  
})  */

/*@RunWith(SpringJUnit4ClassRunner.class)  
@WebAppConfiguration(value = "src/main/webapp")  
@ContextHierarchy({  
        @ContextConfiguration(name = "parent", locations = "classpath:spring-config.xml"),  
        @ContextConfiguration(name = "child", locations = "classpath:spring-mvc.xml")  
})  */
public final class SpringIntegrationTest extends BaseControllerTest {

	/**
	 * 而启动spring 及其mvc环境，然后通过注入方式，可以走完 spring mvc 完整的流程.
	 * 
	 */
	@Resource
	private UserController userController;
	
	//@PathVariable
	
	//@Resource(type = WebApplicationContext.class)
	//@Autowired
	@Resource
    private WebApplicationContext webApplicationContext; 
	
	/**
	 * 引当前项目用其他项目之后，然后可以使用
	 * SpringJunitTest模板测试的其他项目
	 * 
	 * 可以使用所引用目标项目的所有资源
	 * 若引用的项目的配置与本地的冲突或无法生效，需要
	 * 将目标项目的配置复制到当前项目同一路径下
	 * 
	 */
	
	
	/**
	 * 控制器 单元测试
	 * 描述: spring mvc 单元测试 (单元测试只能确保整个业务流程可以跑通)
	 * 直接注入控制器的方式只能做单元测试，不能做系统集成测试，
	 * 例如 可以走 spring mvc 过滤器链、类型转换、数据验证、数据绑定、拦截器等..
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testUnitController() {
		try {
			/**
			 * 构造 request / response 和参数对象
			 * 可以将此测试代码写在要测试的项目中，
			 * 也可以新建一个项目，然后引用需要测试的项目，
			 * 将 spring spring-mvc 系列环境启动起来，就可以测试了.
			 * dao / service / controller / tx / 数据源 ...
			 */
			HttpServletRequest request = new MockHttpServletRequest();
			HttpServletResponse response = new MockHttpServletResponse();
			User user = new User();
			user.setUsername("admin");
			user.setPassword("123456");
			
			userController.login(request, response, user);
			
		} catch (Exception e) {
			log.error("testInjectController =====> ", e);
		}
	}

	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testMockMVC() {
		try {
			MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/sys/login");
			
			//MockMvc mockMvc =  MockMvcBuilders.standaloneSetup(userController).build(); 
			
			MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  
			MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
			
			
		} catch (Exception e) {
			log.error("testMockMVC =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void test() {
		try {
		
			
		} catch (Exception e) {
			log.error("test =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testTemp() {
		try {
			
			
		} catch (Exception e) {
			log.error("testTemp=====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testCommon() {
		try {
			
			
		} catch (Exception e) {
			log.error("testCommon =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSimple() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSimple =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testBase() {
		try {
			
			
		} catch (Exception e) {
			log.error("testBase =====> ", e);
		}
	}
	
	/**
	 * 
	 * 描述: 解决ide静态导入消除问题 
	 * @author qye.zheng
	 * 
	 */
	@Ignore("解决ide静态导入消除问题 ")
	private void noUse() {
		String expected = null;
		String actual = null;
		Object[] expecteds = null;
		Object[] actuals = null;
		String message = null;
		
		assertEquals(expected, actual);
		assertEquals(message, expected, actual);
		assertNotEquals(expected, actual);
		assertNotEquals(message, expected, actual);
		
		assertArrayEquals(expecteds, actuals);
		assertArrayEquals(message, expecteds, actuals);
		
		assertFalse(true);
		assertTrue(true);
		assertFalse(message, true);
		assertTrue(message, true);
		
		assertSame(expecteds, actuals);
		assertNotSame(expecteds, actuals);
		assertSame(message, expecteds, actuals);
		assertNotSame(message, expecteds, actuals);
		
		assertNull(actuals);
		assertNotNull(actuals);
		assertNull(message, actuals);
		assertNotNull(message, actuals);
		
		assertThat(null, null);
		assertThat(null, null, null);
		
		fail();
		fail("Not yet implemented");
		
	}

}
