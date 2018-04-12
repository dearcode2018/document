/**
 * SpringMVCUtil.java
 * @author  qye.zheng
 * 	version 1.0
 */
package com.hua.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * SpringMVCUtil
 * 描述: Spring MVC - 工具类
 * @author  qye.zheng
 */
public final class SpringMVCUtil
{

	/**
	 * 构造方法
	 * 描述: 私有 - 禁止实例化
	 * @author  qye.zheng
	 */
	private SpringMVCUtil()
	{
	}

	/**
	 * 
	 * @description 获取 Spring 父容器 (父上下文)
	 * @param servletContext
	 * @return
	 * @author qye.zheng
	 */
	public static final WebApplicationContext getRootWebApplicationContext(final ServletContext servletContext)
	{
		return WebApplicationContextUtils.getWebApplicationContext(servletContext);
	}
	
	/**
	 * 
	 * @description 获取 Spring 子容器 (子上下文)
	 * @param servletRequest
	 * @return
	 * @author qye.zheng
	 */
	public static final WebApplicationContext getSubWebApplicationContext(final ServletRequest servletRequest)
	{
		return null;
		// return RequestContextUtils.getWebApplicationContext(servletRequest);
	}
	
}
