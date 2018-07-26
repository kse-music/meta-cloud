package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.config.NotifierConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({NotifierConfiguration.NotifierListenerConfiguration.class, NotifierConfiguration.CompositeNotifierConfiguration.class})
public class DingTalkNotifierConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("spring.boot.admin.notify.dingtalk")
    public DingTalkNotifier dingTalkNotifier() {
        return new DingTalkNotifier();
    }

}
