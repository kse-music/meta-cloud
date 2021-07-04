package cn.hiboot.web.conf;

import feign.Feign;
import feign.Target;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * describe about this class
 *
 * @author DingHao
 * @since 2021/7/4 10:43
 */
public final class FeignCircuitBreaker {

    private FeignCircuitBreaker() {
        throw new IllegalStateException("Don't instantiate a utility class");
    }

    public static FeignCircuitBreaker.Builder builder() {
        return new FeignCircuitBreaker.Builder();
    }

    public static final class Builder extends Feign.Builder {

        private CircuitBreakerFactory circuitBreakerFactory;

        private String feignClientName;

        private boolean circuitBreakerGroupEnabled;

        FeignCircuitBreaker.Builder circuitBreakerFactory(CircuitBreakerFactory circuitBreakerFactory) {
            this.circuitBreakerFactory = circuitBreakerFactory;
            return this;
        }

        FeignCircuitBreaker.Builder feignClientName(String feignClientName) {
            this.feignClientName = feignClientName;
            return this;
        }

        FeignCircuitBreaker.Builder circuitBreakerGroupEnabled(boolean circuitBreakerGroupEnabled) {
            this.circuitBreakerGroupEnabled = circuitBreakerGroupEnabled;
            return this;
        }

        public <T> T target(Target<T> target, T fallback) {
            return build(fallback != null ? new FallbackFactory.Default<>(fallback) : null).newInstance(target);
        }

        public <T> T target(Target<T> target, FallbackFactory<? extends T> fallbackFactory) {
            return build(fallbackFactory).newInstance(target);
        }

        @Override
        public <T> T target(Target<T> target) {
            return build(new GlobalFallBackFactory<>(target)).newInstance(target);
        }

        public Feign build(final FallbackFactory<?> nullableFallbackFactory) {
            super.invocationHandlerFactory(
                    (target, dispatch) -> new FeignCircuitBreakerInvocationHandler(circuitBreakerFactory,
                            feignClientName, target, dispatch, nullableFallbackFactory, circuitBreakerGroupEnabled));
            return super.build();
        }

    }


}
