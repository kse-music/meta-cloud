package com.hiekn.metaboot.util;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyClientBuilder;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.Map.Entry;

/**
 * httpClient依赖JerseyClient
 * @author DingHao
 *
 */
public final class HttpUtils {
	
	private static JerseyClient client;
	private static final String acceptContentType = MediaType.APPLICATION_JSON+";"+MediaType.CHARSET_PARAMETER+"=utf-8";
	private static final String requestContentEncode = MediaType.APPLICATION_FORM_URLENCODED+";"+MediaType.CHARSET_PARAMETER+"=utf-8";
	
	static{
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 600000)
		.property(ClientProperties.READ_TIMEOUT, 600000)
		.register(JacksonJsonProvider.class);
		setHttpClient(clientConfig);
	}
	
	/**
	 * 配置client并新建新的client，ClientConfig clientConfig = new ClientConfig();
	 * @author DingHao
	 * @param configuration
	 */
	public static void setHttpClient(final Configuration configuration){
		client = JerseyClientBuilder.createClient(configuration);
	}
	
	/**
	 * 接受接收任意类型返回
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @return 返回任意字符串
	 */
	public static String sendGet(String url,MultivaluedMap<String,Object> query){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().get(String.class);
	}

	public static String sendGet(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().headers(headers).get(String.class);
	}

	/**
	 * 返回application/json字符串自动转成实体bean
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @param cls class类型
	 * @return 实体bean
	 */
	public static <T> T sendGet(String url,MultivaluedMap<String,Object> query,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request(acceptContentType).get(cls);
	}

	public static <T> T sendGet(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request(acceptContentType).headers(headers).get(cls);
	}

	/**
	 * 返回application/json,自动转list、array...
	 * @author DingHao
	 * @param url  请求路径
	 * @param query 参数
	 * @param hsp 泛型类型
	 * @return 返回集合类型数据List<Object>或Array
	 */
	public static <T> T sendGet(String url,MultivaluedMap<String,Object> query,GenericType<T> hsp){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request(acceptContentType).get(hsp);
	}

    public static <T> T sendGet(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query,GenericType<T> hsp){
        WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request(acceptContentType).headers(headers).get(hsp);
    }
	
	/**
	 * 接受接收任意类型返回
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @return 返回任意字符串
	 */
	public static String sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().post(Entity.entity(parsePostParams(post),requestContentEncode),String.class);
	}

	public static String sendPost(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post){
		WebTarget webTarget = parseQueryParams(url,query);
		return webTarget.request().headers(headers).post(Entity.entity(parsePostParams(post),requestContentEncode),String.class);
	}

	/**
	 * 返回application/json字符串自动转成实体bean
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @param cls class类型
	 * @return 实体bean
	 */
	public static <T> T sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request(acceptContentType).post(Entity.entity(parsePostParams(post),requestContentEncode),cls);
	}

	public static <T> T sendPost(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,Class<T> cls){
		WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request(acceptContentType).headers(headers).post(Entity.entity(parsePostParams(post),requestContentEncode),cls);
	}

	/**
	 * 返回application/json,自动转list、array...
	 * @author DingHao
	 * @param url  请求路径
	 * @param query get参数
	 * @param post post参数
	 * @param hsp 泛型类型
	 * @return 返回集合类型数据List<Object>或Array
	 */
	public static <T> T sendPost(String url,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,GenericType<T> hsp){
		WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request(acceptContentType).post(Entity.entity(parsePostParams(post),requestContentEncode),hsp);
	}

	public static <T> T sendPost(String url,MultivaluedMap<String,Object> headers,MultivaluedMap<String,Object> query, MultivaluedMap<String, Object> post,GenericType<T> hsp){
		WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request(acceptContentType).headers(headers).post(Entity.entity(parsePostParams(post),requestContentEncode),hsp);
	}

    /**
     * 发送请求体为text/plain格式数据的请求
     * @param url
     * @param post
     * @return
     */
    public static String sendRawTextPost(String url, MultivaluedMap<String,Object> query,MultivaluedMap<String,Object> post){
        WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request().post(Entity.text(parsePostParams(post)),String.class);
    }

    public static String sendRawTextPost(String url,MultivaluedMap<String,Object> headers, MultivaluedMap<String,Object> query,MultivaluedMap<String,Object> post){
        WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request().headers(headers).post(Entity.text(parsePostParams(post)),String.class);
    }

    /**
     * 发送请求体为json格式数据的请求
     * @param url
     * @param query
     * @param post
     * @return
     */
    public static String sendRawJsonPost(String url, MultivaluedMap<String,Object> query,MultivaluedMap<String,Object> post){
        WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request().post(Entity.json(parsePostParams(post)),String.class);
    }

    public static String sendRawJsonPost(String url,MultivaluedMap<String,Object> headers, MultivaluedMap<String,Object> query,MultivaluedMap<String,Object> post){
        WebTarget webTarget = parseQueryParams(url,query);
        return webTarget.request().headers(headers).post(Entity.json(parsePostParams(post)),String.class);
    }

	private static WebTarget parseQueryParams(String url,MultivaluedMap<String,Object> query){
		WebTarget webTarget = client.target(url);
		if(query != null && query.size() > 0){
			for (Entry<String, List<Object>> item : query.entrySet()) {
				webTarget = webTarget.queryParam(item.getKey(), item.getValue().size()>0?item.getValue().get(0):null);
			}
		}
		return webTarget;
	}

	private static MultivaluedMap<String,String> parsePostParams(MultivaluedMap<String,Object> post){
		MultivaluedMap<String,String> p = new MultivaluedHashMap<>();
		if(post != null && post.size() > 0){
			for (Entry<String, List<Object>> item : post.entrySet()) {
				p.add(item.getKey(), item.getValue().size()>0?item.getValue().get(0).toString():null);
			}
		}
		return p;
	}

}
