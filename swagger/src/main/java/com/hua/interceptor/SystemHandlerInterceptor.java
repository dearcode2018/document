/**
  * @filename SystemHandlerInterceptor.java
  * @description 
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

 /**
 * @type SystemHandlerInterceptor
 * @description Spring 系统拦截器
 * @author qye.zheng
 */
// 或者继承 HandlerInterceptorAdapter
// public final class SystemHandlerInterceptor extends HandlerInterceptorAdapter
public final class SystemHandlerInterceptor implements HandlerInterceptor {

	/* apache commons log */
	protected Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * @description 前置处理 action之前执行
	 * @param request
	 * @param response
	 * @param handler
	 * @return
	 * @throws Exception
	 * @author qye.zheng
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		log.info("preHandle =====> " + request.getRemoteAddr());
		
		return true;
	}

	/**
	 * @description 后置处理 视图生成之前执行
	 * postHandle中，有机会修改ModelAndView
	 * @param request
	 * @param response
	 * @param handler
	 * @param modelAndView
	 * @throws Exception
	 * @author qye.zheng
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * @description 完成后 用于释放资源
	 * @param request
	 * @param response
	 * @param handler
	 * @param ex
	 * @throws Exception
	 * @author qye.zheng
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
