package com.hiekn.metacloud.monitor.ding;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@Getter
@ConfigurationProperties("spring.boot.admin.notify.dingtalk")
public class DingTalkNotifierProperties {

    private final String atMobiles;

    @ConstructorBinding
    public DingTalkNotifierProperties(String atMobiles) {
        this.atMobiles = atMobiles;
    }

}
