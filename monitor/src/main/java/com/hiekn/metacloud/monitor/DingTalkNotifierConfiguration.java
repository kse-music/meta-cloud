package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.boot.admin.notify.dingtalk", name = "webhook-url")
public class DingTalkNotifierConfiguration {

    @Bean
    @ConfigurationProperties("spring.boot.admin.notify.dingtalk")
    public DingTalkNotifier dingTalkNotifier(InstanceRepository repository) {
        return new DingTalkNotifier(repository);
    }

}
