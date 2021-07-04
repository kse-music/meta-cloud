package cn.hiboot.web.conf;

import cn.hiboot.mcn.core.model.result.RestResp;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/4 10:18
 */
@Slf4j
@AllArgsConstructor
public class FeignFallback<T> implements MethodInterceptor {

    private final Class<T> targetType;
    private final String targetName;
    private final Throwable cause;

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String errorMessage = cause.getMessage();
        log.error("FunFeignFallback:[{}.{}] serviceId:[{}] message:[{}]", targetType.getName(), method.getName(), targetName, errorMessage);
        int DEFAULT_CODE = 999999;
        if (!(cause instanceof FeignException)) {
            return new RestResp<>(DEFAULT_CODE,errorMessage);
        }
        FeignException exception = (FeignException) cause;
        if(exception.contentUTF8().isEmpty()){
            return new RestResp<>(DEFAULT_CODE,errorMessage);
        }
        return new RestResp<>(DEFAULT_CODE,exception.contentUTF8());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeignFallback<?> that = (FeignFallback<?>) o;
        return targetType.equals(that.targetType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(targetType);
    }

}
