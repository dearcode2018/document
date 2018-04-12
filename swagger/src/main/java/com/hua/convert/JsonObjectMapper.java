package com.hua.convert;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;


/**
 * @description:
 * @date:
 * @author:钟伟雄
 */
public class JsonObjectMapper extends ObjectMapper
{
	private Log log = LogFactory.getLog(this.getClass().getName());

	public JsonObjectMapper()
	{
		super();
		// 空值处理为空串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>()
		{
			@Override
			public void serialize(Object value, JsonGenerator jg, SerializerProvider sp) throws IOException, JsonProcessingException
			{
				jg.writeString("");
			}
		});
	}
}