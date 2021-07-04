package cn.hiboot.web.conf;

import feign.Feign;
import feign.InvocationHandlerFactory;
import feign.Target;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static feign.Util.checkNotNull;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/4 10:46
 */
public class FeignCircuitBreakerInvocationHandler implements InvocationHandler {

    private final CircuitBreakerFactory factory;

    private final String feignClientName;

    private final Target<?> target;

    private final Map<Method, InvocationHandlerFactory.MethodHandler> dispatch;

    private final FallbackFactory<?> nullableFallbackFactory;

    private final Map<Method, Method> fallbackMethodMap;

    private final boolean circuitBreakerGroupEnabled;

    FeignCircuitBreakerInvocationHandler(CircuitBreakerFactory factory, String feignClientName, Target<?> target,
                                         Map<Method, InvocationHandlerFactory.MethodHandler> dispatch, FallbackFactory<?> nullableFallbackFactory,
                                         boolean circuitBreakerGroupEnabled) {
        this.factory = factory;
        this.feignClientName = feignClientName;
        this.target = checkNotNull(target, "target");
        this.dispatch = checkNotNull(dispatch, "dispatch");
        this.fallbackMethodMap = toFallbackMethod(dispatch);
        this.nullableFallbackFactory = nullableFallbackFactory;
        this.circuitBreakerGroupEnabled = circuitBreakerGroupEnabled;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        // early exit if the invoked method is from java.lang.Object
        // code is the same as ReflectiveFeign.FeignInvocationHandler
        if ("equals".equals(method.getName())) {
            try {
                Object otherHandler = args.length > 0 && args[0] != null ? Proxy.getInvocationHandler(args[0]) : null;
                return equals(otherHandler);
            }
            catch (IllegalArgumentException e) {
                return false;
            }
        }
        else if ("hashCode".equals(method.getName())) {
            return hashCode();
        }
        else if ("toString".equals(method.getName())) {
            return toString();
        }
        String circuitName = Feign.configKey(target.type(), method);
        CircuitBreaker circuitBreaker = circuitBreakerGroupEnabled ? factory.create(circuitName, feignClientName)
                : factory.create(circuitName);
        Supplier<Object> supplier = asSupplier(method, args);
        if (this.nullableFallbackFactory != null) {
            Function<Throwable, Object> fallbackFunction = throwable -> {
                Object fallback = this.nullableFallbackFactory.create(throwable);
                try {
                    return this.fallbackMethodMap.get(method).invoke(fallback, args);
                }
                catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            };
            return circuitBreaker.run(supplier, fallbackFunction);
        }
        return circuitBreaker.run(supplier);
    }

    private Supplier<Object> asSupplier(final Method method, final Object[] args) {
        final RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return () -> {
            try {
                RequestContextHolder.setRequestAttributes(requestAttributes);
                return this.dispatch.get(method).invoke(args);
            }
            catch (RuntimeException throwable) {
                throw throwable;
            }
            catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
            finally {
                RequestContextHolder.resetRequestAttributes();
            }
        };
    }

    static Map<Method, Method> toFallbackMethod(Map<Method, InvocationHandlerFactory.MethodHandler> dispatch) {
        Map<Method, Method> result = new LinkedHashMap<Method, Method>();
        for (Method method : dispatch.keySet()) {
            method.setAccessible(true);
            result.put(method, method);
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FeignCircuitBreakerInvocationHandler) {
            FeignCircuitBreakerInvocationHandler other = (FeignCircuitBreakerInvocationHandler) obj;
            return this.target.equals(other.target);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.target.hashCode();
    }

    @Override
    public String toString() {
        return this.target.toString();
    }

}
