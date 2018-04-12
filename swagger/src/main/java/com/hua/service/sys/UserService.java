/**
  * @filename UserService.java
  * @description  
  * @version 1.0
  * @author qye.zheng
 */
package com.hua.service.sys;

import com.hua.bean.ResultBean;
import com.hua.entity.User;
import com.hua.service.CoreService;

 /**
 * @type UserService
 * @description  
 * @author qye.zheng
 */
public interface UserService extends CoreService
{
	/**
	 * 
	 * @description 
	 * @param user
	 * @return
	 * @author qye.zheng
	 */
	public ResultBean login(final User user);
}
