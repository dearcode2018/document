/**
  * @filename UserController.java
  * @description  
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hua.bean.Pager;
import com.hua.bean.ResultBean;
import com.hua.constant.UriConstant;
import com.hua.entity.User;
import com.hua.entity.UserLog;
import com.hua.service.sys.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.ResponseHeader;

 /**
 * @type UserController
 * @description  
 * @author qye.zheng
 */
@Controller
@RequestMapping(UriConstant.API + "sys")
public final class UserController extends BaseController
{
	
	@Resource
	private UserService userService;
	
	/**
	 * 
	 * @description 
	 * @param request
	 * @param response
	 * @param user
	 * @return
	 * @author qianye.zheng
	 */
	 @ApiOperation(value = "登录接口啊", notes = "详细描述，哈哈",
	            tags = {"标签1", "标签2"}, response = ResultBean.class,
	            responseContainer = "Map", responseReference = "responseReferencexx",
	            httpMethod = "POST", nickname = "昵称哈哈", produces = "application/json, application/xml",
	            consumes = "application/json", protocols = "http, https, ws",  authorizations = {@Authorization(value = "xx")},
	            hidden = false, responseHeaders = {@ResponseHeader(name = "响应头xx")}, code = 300, 
	            extensions = {@Extension(name = "h1", properties = {@ExtensionProperty(name = "1", value = "2")})}
			 )
	@RequestMapping("login")
	@ResponseBody
	public final ResultBean login(final HttpServletRequest request, 
			final HttpServletResponse response, @ApiParam(name = "user", value="用户信息" ,required = true, 
			examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = "{\"id\": \"string\",\"lastLoginIp\": \"string\","
					+ "\"nickname\": \"222\", \"password\": \"3343454545\",\"username\": \"string\",\"valid\": true}"))) @RequestBody final User user)
	{
		log.info("login =====> enter ...");
		
		return userService.login(user);
	}

	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @param pager
	  * @param userLog
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "moreParams接口啊", notes = "详细描述，哈哈",
	            httpMethod = "POST", response = ResultBean.class, nickname = "昵称，哈哈", consumes = "application/json", 
	            produces = "application/json", protocols = "http")
	@RequestMapping("moreParams")
	@ResponseBody
	public final ResultBean moreParams(final HttpServletRequest request, 
			final HttpServletResponse response, final User user, final Pager<User> pager, final UserLog userLog)
	{
		log.info("moreParams =====> enter ...");
		// 接收参数1
		log.info("moreParams =====> " + user.getUsername());
		
		// 接收参数2
		log.info("moreParams =====> " + pager.getPageSize());
		
		// 接收参数3
		log.info("moreParams =====> " + userLog.getUsername());
		log.info("moreParams =====> " + userLog.getLoginIp());
		
		
		return new ResultBean();
	}
	
}
