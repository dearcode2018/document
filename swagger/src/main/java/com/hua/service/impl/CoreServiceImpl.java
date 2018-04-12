/**
 * 描述: 
 * CoreServiceImpl.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.hua.service.CoreService;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * CoreServiceImpl
 */
//@Service
public abstract class CoreServiceImpl implements CoreService {

	/* apache commons log */
	protected Log log = LogFactory.getLog(this.getClass().getName());
	
}
