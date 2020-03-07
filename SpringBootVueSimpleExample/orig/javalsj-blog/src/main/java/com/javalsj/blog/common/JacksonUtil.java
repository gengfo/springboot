package com.javalsj.blog.common;

import java.io.IOException;
import java.io.StringWriter;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @description 使用Bean转JSON格式或者JSON转Bean格式工具类
 * @author WANGJIHONG
 * @date 2018年2月25日 下午7:03:42
 * @Copyright 版权所有 (c) www.javalsj.com
 * @memo 无备注说明
 */
public class JacksonUtil {
	
	private static ObjectMapper objectMapper;
	
	static {
		objectMapper = new ObjectMapper();
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
	}

	public static final ObjectMapper getObjectMapperInstance() {
		return objectMapper;
	}
	

	public static String bean2Json(Object obj) throws IOException {
		StringWriter stringWriter = new StringWriter();
		JsonGenerator jsonGenerator = new JsonFactory().createGenerator(stringWriter);
		objectMapper.writeValue(jsonGenerator, obj);
		jsonGenerator.close();
		return stringWriter.toString();
	}

	public static <T> T json2Bean(String jsonStr, Class<T> objClass)
			throws JsonParseException, JsonMappingException, IOException {
		return objectMapper.readValue(jsonStr, objClass);
	}
}
