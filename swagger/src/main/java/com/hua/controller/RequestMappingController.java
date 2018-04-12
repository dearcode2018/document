/**
 * 描述: 
 * RequestMappingController.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hua.bean.ResultBean;

/**
 * 描述: 
 * @author qye.zheng
 * 
 * RequestMappingController
 */
// 控制器
@Controller
@RequestMapping(value={"/requestMapping"}, method = RequestMethod.GET)
//@RequestMapping(value={"/"})
public final class RequestMappingController extends BaseController
{
	
	/**
	 * 
	 * 描述: 
	 * @author qye.zheng
	 * 
	 * @param request
	 * @param response
	 * @param path
	 * @return
	 */
	// 匹配任意非空白字符串 regex = "\\S+";
	// 逐个匹配的方式，取单个值 @RequestMapping(value={"/file/{path}/a/b"})
	// 逐个匹配，取多个值 @RequestMapping(value="/user/{userId}/roles/{roleId}",method = RequestMethod.GET)  
	// 使用正则的方式，不支持 / . ，另外 . 需要在 正则中另外匹配 例如{"/file/{path:\\S+\\.\\S*}"
	//@RequestMapping(value={"/file/{path:/?\\S+}"})
	@ResponseBody
	public ResultBean search(final HttpServletRequest request, 
			final HttpServletResponse response, @PathVariable("path") final String path) {
		log.info("search =====> path = " + path);
		
		return null;
	}
	
}
