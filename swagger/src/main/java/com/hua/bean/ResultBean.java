/**
 * 描述: 
 * ResultBean.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * ResultBean
 */
//@XmlRootElement(name = "resultBean", namespace = "http://spring.hua.com")
@ApiModel("ResultBeanxx")
public final class ResultBean extends BaseBean {

	 /* long */
	private static final long serialVersionUID = -5368102181711261710L;
	
	/* 成功或失败 */
	@ApiModelProperty(value = "成功或失败", required = true, access = "")
	private boolean success = true;
	
	/* 消息码 */
	@ApiModelProperty(value = "消息码", required = true, access = "")
	private String messageCode = "";
	
	/* 返回给客户端的消息 */
	@ApiModelProperty(value = "回给客户端的消息", required = true, access = "")
	private String message = "";
	
	/* 返回给客户端的数据 */
	@ApiModelProperty(value = "返回给客户端的数据", required = true, access = "")
	private Object data = "";

	/**
	 * 
	 * @description 构造方法
	 * @author qye.zheng
	 */
	public ResultBean()
	{
		super();
	}

	public ResultBean(boolean success, String message)
	{
		super();
		this.success = success;
		this.message = message;
	}

	/**
	 * @return the success
	 */
	public final boolean isSuccess()
	{
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public final void setSuccess(boolean success)
	{
		this.success = success;
	}

	/**
	 * @return the messageCode
	 */
	public final String getMessageCode()
	{
		return messageCode;
	}

	/**
	 * @param messageCode the messageCode to set
	 */
	public final void setMessageCode(String messageCode)
	{
		this.messageCode = messageCode;
	}

	/**
	 * @return the message
	 */
	public final String getMessage()
	{
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public final void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return the data
	 */
	public final Object getData()
	{
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(Object data)
	{
		this.data = data;
	}
	
}
