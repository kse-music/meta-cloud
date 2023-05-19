package com.hiekn.metacloud.monitor.ding;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("spring.boot.admin.notify.dingtalk")
@ConstructorBinding
public class DingTalkNotifierProperties {

    private final String atMobiles;

    public DingTalkNotifierProperties(String atMobiles) {
        this.atMobiles = atMobiles;
    }

    public String getAtMobiles() {
        return atMobiles;
    }

}
