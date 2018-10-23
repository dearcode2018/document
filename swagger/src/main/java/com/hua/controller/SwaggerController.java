/**
  * @filename SwaggerController.java
  * @description  
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hua.bean.Pager;
import com.hua.bean.ResultBean;
import com.hua.constant.UriConstant;
import com.hua.entity.SimpleUser;
import com.hua.entity.User;
import com.hua.entity.UserLog;
import com.hua.service.sys.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.Contact;
import io.swagger.annotations.Example;
import io.swagger.annotations.ExampleProperty;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.Info;
import io.swagger.annotations.ResponseHeader;
import io.swagger.annotations.SwaggerDefinition;

 /**
 * @type SwaggerController
 * @description  
 * @author qye.zheng
 */
// @Api 类描述
//@Api(value = "/api/sys", tags = {"用户标签"}, protocols = "http", hidden = true)
@Api(value = "/api/sys", tags = {"Controller标签"})
// @ApiIgnore 表示忽略该类，不生成文档
//@ApiIgnore
@SwaggerDefinition(info = 
@Info(title = "标题", version = "版本", contact = 
@Contact(name = "qianye.zheng", email = "xx@qq.com", url = "主页")))
@Controller
@RequestMapping(UriConstant.API + "sys")
public class SwaggerController extends BaseController
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
	/*
	 * @ApiOperation 标注在方法上，说明该方法/接口的具体用法
	 * 
	 */
	 @ApiOperation(value = "登录接口V1", notes = "POST方式接收application/json参数",
	            tags = {"POST方法"}, response = ResultBean.class,
	            responseContainer = "Map", responseReference = "responseReferencexx",
	            // 方法: GET | POST
	            httpMethod = "POST", 
	            nickname = "别名/昵称哈哈", 
	            // 请求数据类型 (输入，输入去消费)
	            consumes = "application/json", 
	            // 响应数据类型 (输出，生产去输出)
	            produces = "application/json, application/xml",
	            protocols = "http, https, ws",  authorizations = {@Authorization(value = "xx")},
	            hidden = false, responseHeaders = {@ResponseHeader(name = "响应头xx")}, code = 300, 
	            extensions = {@Extension(name = "h1", properties = {@ExtensionProperty(name = "1", value = "2")})}
			 )
	@RequestMapping("loginV1")
	@ResponseBody
	/*
	 * @ApiParam(name = "user", value="用户信息" ,required = true, 
			examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = "{\"id\": \"string\",\"lastLoginIp\": \"string\","
					+ "\"nickname\": \"222\", \"password\": \"3343454545\",\"username\": \"string\",\"valid\": true}"))) @RequestBody final SimpleUser user
	 */
	 @ApiParam(name = "id", value="用户信息" ,required = true, 
		examples = @Example(value = @ExampleProperty(mediaType = "application/json", value = "{'id':'1234'}")))
	public final ResultBean loginV1(final HttpServletRequest request, 
			final HttpServletResponse response, @RequestBody final SimpleUser user)
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
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V2", notes = "POST方式接收x-www-form-urlencoded参数",
	            tags = {"POST方法"}, response = ResultBean.class,
	            responseContainer = "Map", responseReference = "responseReferencexx",
	            // 方法: GET | POST
	            httpMethod = "POST", 
	            nickname = "别名/昵称哈哈1", 
	            // 请求数据类型 (输入，输入去消费)
	            //consumes = "application/json", 
	            consumes = "application/x-www-form-urlencoded", 
	            // 响应数据类型 (输出，生产去输出)
	            //	produces = "application/json, application/xml",
	            produces = "application/json",
	            protocols = "http, https, ws",  authorizations = {@Authorization(value = "xx")},
	            hidden = false, responseHeaders = {@ResponseHeader(name = "响应头xx")}, code = 300, 
	            extensions = {@Extension(name = "h1", properties = {@ExtensionProperty(name = "1", value = "2")})}
			 )
	@RequestMapping(value = "loginV2", method = RequestMethod.POST)
	@ResponseBody
	public final ResultBean loginV2(final HttpServletRequest request, 
			final HttpServletResponse response, @ApiParam(name = "user", value="用户信息" ,required = true, 
			examples = @Example(value = @ExampleProperty(mediaType = "application/x-www-form-urlencoded", value = "{\"id\": \"string\",\"lastLoginIp\": \"string\","
					+ "\"nickname\": \"222\", \"password\": \"3343454545\",\"username\": \"string\",\"valid\": true}"))) final SimpleUser user)
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
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V20", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"}, response = ResultBean.class,
	            responseContainer = "Map", responseReference = "responseReferencexx",
	            // 方法: GET | POST
	            httpMethod = "GET", 
	            nickname = "别名/昵称哈哈3434", 
	            // 请求数据类型 (输入，输入去消费)
	            //consumes = "application/json", 
	            consumes = "application/x-www-form-urlencoded", 
	            // 响应数据类型 (输出，生产去输出)
	            //	produces = "application/json, application/xml",
	            produces = "application/json",
	            protocols = "http, https, ws",  authorizations = {@Authorization(value = "xx")},
	            hidden = false, responseHeaders = {@ResponseHeader(name = "响应头xx")}, code = 300, 
	            extensions = {@Extension(name = "h1", properties = {@ExtensionProperty(name = "1", value = "2")})}
			 )
	@RequestMapping(value = "loginV20", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV20(final HttpServletRequest request, 
			final HttpServletResponse response,  final SimpleUser user)
	{
		log.info("login =====> enter ..." + request.getHeader("Content-Type"));
		
		return userService.login(user);
	}
	 
	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V30", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"})
	 @ApiImplicitParam(paramType = "query", name = "username", 
	 	dataType = "String", required = true ,defaultValue = "lisi")
	@RequestMapping(value = "loginV30", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV30(final HttpServletRequest request, 
			final HttpServletResponse response, @ApiParam(name = "user", value="用户信息" ,required = true, 
			examples = @Example(value = @ExampleProperty(mediaType = "application/x-www-form-urlencoded", value = "{\"id\": \"string\",\"lastLoginIp\": \"string\","
					+ "\"nickname\": \"222\", \"password\": \"3343454545\",\"username\": \"string\",\"valid\": true}"))) final SimpleUser user)
	{
		log.info("login =====> enter ..." + request.getHeader("Content-Type"));
		
		return userService.login(user);
	}	 
	 
	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V31", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"})
	 @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "username", 
			 	dataType = "string", required = true ,defaultValue = "lisi"),
		 @ApiImplicitParam(paramType = "query", name = "nickname", 
		 	dataType = "string", required = true ,defaultValue = "李四"),
		 @ApiImplicitParam(paramType = "query", name = "password", 
		 	dataType = "string", required = true ,defaultValue = "12456888")
	 })
	@RequestMapping(value = "loginV31", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV31(final HttpServletRequest request, 
			final HttpServletResponse response, @ApiParam(name = "user", value="用户信息" ,required = true, 
			examples = @Example(value = @ExampleProperty(mediaType = "application/x-www-form-urlencoded", value = "{\"id\": \"string\",\"lastLoginIp\": \"string\","
					+ "\"nickname\": \"222\", \"password\": \"3343454545\",\"username\": \"string\",\"valid\": true}"))) final SimpleUser user)
	{
		log.info("login =====> enter ..." + request.getHeader("Content-Type"));
		
		return userService.login(user);
	}	 
	 
	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V32", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"})
	 @ApiImplicitParams({
		 @ApiImplicitParam(paramType = "query", name = "username", 
			 	dataType = "string", required = true ,defaultValue = "lisi"),
		 @ApiImplicitParam(paramType = "header", name = "someKey", 
		 	dataType = "string", required = true ,defaultValue = "key111")
	 })
	@RequestMapping(value = "loginV32", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV32(final HttpServletRequest request, 
			final HttpServletResponse response,  
			final @RequestParam("username") String username,
			final @RequestHeader("someKey") String someKey
			)
	{
		log.info("login =====> enter ..." + username + ", someKey = " + someKey);
		
		return new ResultBean();
	}	
	 
	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V33", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"})
	 @ApiImplicitParams({
		 @ApiImplicitParam(paramType = "path", name = "code", 
			 	dataType = "string", required = true ,defaultValue = "lisi"),
		 @ApiImplicitParam(paramType = "header", name = "someKey", 
		 	dataType = "string", required = true ,defaultValue = "key111")
	 })
	@RequestMapping(value = "loginV33/{code}", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV33(final HttpServletRequest request, 
			final HttpServletResponse response,  
			final @PathVariable("code") String code
			)
	{
		log.info("login =====> enter ..." + code);
		
		return new ResultBean();
	}	

	 /**
	  * 
	  * @description 
	  * @param request
	  * @param response
	  * @param user
	  * @return
	  * @author qianye.zheng
	  */
	 @ApiOperation(value = "登录接口V34", notes = "GET方式接收x-www-form-urlencoded参数",
	            tags = {"GET方法"})
	 @ApiImplicitParams({
		 @ApiImplicitParam(paramType = "query", name = "username", 
			 	dataType = "string", required = true ,defaultValue = "lisi"),
		 @ApiImplicitParam(paramType = "header", name = "someKey", 
		 	dataType = "string", required = true ,defaultValue = "key111")
	 })
	 // 在路径中的值，也可以通过@RequestParam获取
	@RequestMapping(value = "loginV34/{code}", method = RequestMethod.GET)
	@ResponseBody
	public final ResultBean loginV34(final HttpServletRequest request, 
			final HttpServletResponse response,  
			final @RequestParam("code") String code
			)
	{
		log.info("login =====> enter ..." + code);
		
		return new ResultBean();
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
