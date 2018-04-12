package com.hua.convert;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import com.hua.util.DateTimeUtil;

/**
 * @description:
 * @date:
 * @author:钟伟雄
 */
public class ParameterResolver implements WebArgumentResolver
{
	private static Set<String> ignoreClassNameSet = new LinkedHashSet<String>();

	static
	{
		ignoreClassNameSet.add(Boolean.class.getName());
		ignoreClassNameSet.add("boolean");
		ignoreClassNameSet.add(Byte.class.getName());
		ignoreClassNameSet.add("byte");
		ignoreClassNameSet.add(Short.class.getName());
		ignoreClassNameSet.add("short");
		ignoreClassNameSet.add(Integer.class.getName());
		ignoreClassNameSet.add("int");
		ignoreClassNameSet.add(Long.class.getName());
		ignoreClassNameSet.add("long");
		ignoreClassNameSet.add(Float.class.getName());
		ignoreClassNameSet.add("float");
		ignoreClassNameSet.add(Double.class.getName());
		ignoreClassNameSet.add("double");
		ignoreClassNameSet.add(Date.class.getName());
		ignoreClassNameSet.add(String.class.getName());
		ignoreClassNameSet.add(HttpServletRequest.class.getName());
		ignoreClassNameSet.add(HttpServletResponse.class.getName());
		ignoreClassNameSet.add(WebDataBinder.class.getName());
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest request) throws Exception
	{
		Class cls = methodParameter.getParameterType();
		System.out.println("className = " + cls.getName());
		if (!ignoreClassNameSet.contains(cls.getName()))
		{
			String parameterName = methodParameter.getParameterName();
			if (parameterName == null || "".equals(parameterName))
			{
				parameterName = cls.getSimpleName();
				parameterName = parameterName.substring(0, 1).toLowerCase() + parameterName.substring(1);
			}
			Iterator<String> it = request.getParameterNames();
			Map<String, String> paramsMap = new LinkedHashMap<String, String>();
			String key = null;
			while (it.hasNext())
			{
				key = it.next();
				if (key.startsWith(parameterName + "."))
				{
					paramsMap.put(key, request.getParameter(key));
				}
			}
			if (!paramsMap.isEmpty())
			{
				Object obj = cls.newInstance();
				BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
				beanWrapper.setAutoGrowNestedPaths(true);
				beanWrapper.registerCustomEditor(Date.class, new PropertyEditorSupport()
				{
					public void setAsText(String value)
					{
						setValue(DateTimeUtil.parseStandardDate(value));
					}

					public String getAsText()
					{
						return super.getAsText();
					}

				});
				Object value = null;
				for (String paraName : paramsMap.keySet())
				{
					key = paraName.substring(parameterName.length() + 1);
					value = paramsMap.get(paraName);
					if (value != null && "".equals(value))
					{
						beanWrapper.setPropertyValue(key, value);
					}
				}
				paramsMap.clear();
				request.setAttribute(parameterName, obj, 0);
				return obj;
			}
		}
		return UNRESOLVED;
	}
}
