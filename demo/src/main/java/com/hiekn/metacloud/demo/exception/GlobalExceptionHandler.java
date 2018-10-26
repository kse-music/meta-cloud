package com.hiekn.metacloud.demo.exception;

import com.google.common.collect.Lists;
import com.hiekn.metacloud.demo.bean.result.ErrorCodes;
import com.hiekn.metacloud.demo.bean.result.RestResp;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestControllerAdvice
@ResponseBody
public class GlobalExceptionHandler  {

    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @Value("${jersey.swagger.base-package}")
    private String basePackage;

    @ExceptionHandler(Exception.class)
    public RestResp<Object> handler(HttpServletRequest request, Exception exception) throws Exception {

        ErrorCodes code = ErrorCodes.SERVICE_ERROR;
        String errMsg = "";

        if(exception instanceof BaseException){
            code = ((BaseException) exception).getCode();
            errMsg = ((BaseException) exception).getMsg();
        }else if(exception instanceof ServletException){
            code = ErrorCodes.HTTP_ERROR;
            if(exception instanceof NoHandlerFoundException){//404

            }else if(exception instanceof HttpRequestMethodNotSupportedException){//405

            }else if(exception instanceof HttpMediaTypeException){//406

            }
        }

        Integer errorCode = code.getErrorCode();
        errMsg = StringUtils.isBlank(errMsg)?code.getInfo():errMsg;
        //只打印业务代码异常栈
        exception.setStackTrace(Lists.newArrayList(exception.getStackTrace()).stream().filter(s -> s.getClassName().contains(basePackage)).collect(Collectors.toList()).toArray(new StackTraceElement[]{}));
        logger.error(errorCode,exception);
        return new RestResp<>(errorCode,errMsg);
    }

}
