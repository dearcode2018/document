/**
 * 描述: 
 * BaseControllerTest.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.test;

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
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;


/**
 * 描述: 
 * 
 * @author qye.zheng
 * BaseControllerTest
 */
/*
 * 
 * @SpringJUnit4ClassRunner 运行器负责拉起 spring 环境
 * @ContextConfiguration 指定 spring配置文件，若不指定，则使用默认配置.
 */
// for Junit 4.x
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:conf/xml/applicationContext.xml"})
/*@ContextConfiguration(locations = {"classpath:conf/xml/spring-bean.xml", "classpath:conf/xml/spring-config.xml", 
		"classpath:conf/xml/spring-mvc.xml", "classpath:conf/xml/spring-service.xml"})*/
public class BaseControllerTest extends BaseTest {

	/**
	 * 基类中书写这个2个属性，实际上是在子类中
	 * 通过 @ContextConfiguration 启动spring mvc 环境，从而实现这2个属性的注入
	 */
	
	//@Resource(name = "defaultAnnotationHandlerMapping#0")
	//@Resource(name = "simpleUrlHandlerMapping#0")
	public HandlerMapping handlerMapping;
	
	//@Resource
	//public HandlerAdapter handlerAdapter;
	
	/**
	 * MockHttpServletRequest 模仿HttpServletRequest对象，
	 * 通过直接new的方式构造该对象，设置请求地址以及请求
	 * 参数之后，通过 HandlerMapping 和 HandlerAdapter  来请求spring mvc.
	 */
	
	/**
	 * 
	 * @description 执行请求处理
	 * @param request
	 * @param response
	 * @return
	 * @author qye.zheng
	 */
	public final ModelAndView execute(final HttpServletRequest request, final HttpServletResponse response)
	{
		return null;
	}
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 */
	@Test
	public void testSpringJunit() {
		try {
			
			
		} catch (Exception e) {
			log.error("testSpringJunit =====> ", e);
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
