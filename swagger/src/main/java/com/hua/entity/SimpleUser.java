/**
 * 描述: 
 * SimpleUser.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.entity;

import com.hua.bean.BaseBean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * SimpleUser
 */
//@ApiModel("简单用户")
public final class SimpleUser extends BaseBean {

	 /* long */
	private static final long serialVersionUID = 1L;
	
	/* 登录-用户名 (唯一) */
	//@ApiModelProperty(value = "登录-用户名 (唯一)", required = true)
	private String username;
	
	/* 昵称 (用于显示) */
	@ApiModelProperty(value = "昵称 (用于显示) ", required = false, access = "")
	private String nickname;
	
	/* 登录-密码 */
	@ApiModelProperty(value = "登录-密码", required = true, example = "123456")
	private String password;
	
	
	/* 用户状态 - 是否有效 默认 : 有效 */
	@ApiModelProperty(value = "用户状态 - 是否有效 默认 : 有效", required = false, allowableValues = "true,false")
	private boolean valid = true;
	
	
	/* 上一次登录-IP地址 */
	@ApiModelProperty(value = "上一次登录-IP地址", required = false, name = "xxxname")
	private String lastLoginIp;
	
	/** 无参构造方法 */
	public SimpleUser() {}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the lastLoginIp
	 */
	public String getLastLoginIp() {
		return lastLoginIp;
	}

	/**
	 * @param lastLoginIp the lastLoginIp to set
	 */
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname()
	{
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	/**
	 * @return the valid
	 */
	public boolean isValid()
	{
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	
}
