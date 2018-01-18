package com.hiekn.metaboot.util;

import com.google.gson.Gson;
import com.hiekn.metaboot.exception.JsonException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Type;

@Configuration
public class JsonUtils {

    private static Gson gson ;

    @Autowired
    public void setGson(Gson gson) {
        JsonUtils.gson = gson;
    }

    public static <T> T fromJson(String json, Class<T> cls) {
		try {
			return gson.fromJson(json, cls);
		} catch (Exception e) {
			throw JsonException.newInstance();
		}
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		try {
			return gson.fromJson(json, typeOfT);
		} catch (Exception e) {
			throw JsonException.newInstance(); 
		}
	}

	public static String toJson(Object obj) {
		try {
			return gson.toJson(obj);
		} catch (Exception e) {
			throw JsonException.newInstance(); 
		}
	}

}