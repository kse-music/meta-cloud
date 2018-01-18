package com.hiekn.metaboot.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.List;

/**
 * GUAVA CACHE UTIL
 * @author DH
 *
 */
public final class CacheUtils {
	
	private static Cache<String, Object> cache = CacheBuilder.newBuilder().build();  
	
	public static void setCache(String key,Object value){
		cache.put(key, value);  
	}
	
	public static String getCacheToString(String key){
		return String.valueOf(getCache(key));  
	}
	
	public static Integer getCacheToInt(String key){
		return Integer.valueOf(getCacheToString(key));  
	}
	
	public static Long getCacheToLong(String key){
		return Long.valueOf(getCacheToString(key));  
	}
	
	public static Object getCache(String key){
		return cache.getIfPresent(key);  
	}
	
	public static void clearAllCache(){
		cache.invalidateAll();
	}
	
	public static void clearCache(String key){
		cache.invalidate(key);
	}
	
	public static void clearCache(List<String> keys){
		cache.invalidateAll(keys);
	}
	
}
