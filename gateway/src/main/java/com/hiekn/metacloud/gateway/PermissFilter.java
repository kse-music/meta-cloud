package com.hiekn.metacloud.gateway;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 1.filterType方法的返回值为过滤器的类型，过滤器的类型决定了过滤器在哪个生命周期执行，pre表示在路由之前执行过滤器，
 其他可选值还有post、error、route和static，当然也可以自定义。
 2.filterOrder方法表示过滤器的执行顺序，当过滤器很多时，这个方法会有意义。
 3.shouldFilter方法用来判断过滤器是否执行，true表示执行，false表示不执行，在实际开发中，
 我们可以根据当前请求地址来决定要不要对该地址进行过滤，这里我直接返回false。
 4.run方法则表示过滤的具体逻辑，假设请求地址中携带了login参数的话，则认为是合法请求，
 否则就是非法请求，如果是非法请求的话，首先设置ctx.setSendZuulResponse(false);表示不对该请求进行路由，
 然后设置响应码和响应值。这个run方法的返回值在当前版本(Dalston.SR3)中暂时没有任何意义，可以返回任意值。
 */
@Component
public class PermissFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        request.getParameterMap();  // 一定要get一下,下面这行代码才能取到值.
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (Objects.isNull(requestQueryParams)) {
            requestQueryParams = Maps.newHashMap();
        }
        requestQueryParams.put("userId", Lists.newArrayList("12312"));
        ctx.setRequestQueryParams(requestQueryParams);

        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String s : requestQueryParams.keySet()) {
            if(parameterMap.containsKey(s)){
                parameterMap.remove(s);
            }
        }
        String body = null;
        try {
            InputStream in = ctx.getRequest().getInputStream();
            body = StreamUtils.copyToString(in, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(body);
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String key = entry.getKey();
            String[] value = entry.getValue();
            if(body.indexOf(key+"=") != -1){
                body = StringUtils.replace(body, key + "=" + value[0], key + "=" + trans(value[0]));
            }
        }
        final byte[] reqBodyBytes = body.getBytes();
        ctx.setRequest(new HttpServletRequestWrapper(request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new ServletInputStreamWrapper(reqBodyBytes);
            }

            @Override
            public int getContentLength() {
                return reqBodyBytes.length;
            }

            @Override
            public long getContentLengthLong() {
                return reqBodyBytes.length;
            }
        });

//        String login = request.getParameter("login");
//        if (login == null) {
//            ctx.setSendZuulResponse(false);
//            ctx.setResponseStatusCode(401);
//            ctx.addZuulResponseHeader("content-type","text/html;charset=utf-8");
//            ctx.setResponseBody("<h1><a href='http://www.baidu.com'>非法访问</a></h1>");
//        }
        return null;
    }

    private String trans(String s) {
        return "new_"+s;
    }
}