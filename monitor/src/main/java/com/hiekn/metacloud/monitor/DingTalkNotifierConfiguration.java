package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "spring.boot.admin.notify.dingtalk")
@Setter
@Getter
public class DingTalkNotifierConfiguration {

    private String webhookToken;
    private String atMobiles;
    private String msgtype = "markdown";
    private String title = "服务通知";

    @ConstructorBinding
    public DingTalkNotifierConfiguration(String atMobiles,String webhookToken) {
        this.atMobiles = atMobiles;
        this.webhookToken = webhookToken;
    }

    public HttpEntity<Map<String, Object>> create(InstanceEvent event, Instance instance) {
        String msg = "";
        if(event instanceof InstanceStatusChangedEvent){//只关心状态变化
            InstanceStatusChangedEvent changedEvent = (InstanceStatusChangedEvent) event;
            StringBuilder sb = new StringBuilder("\n >**");
            sb.append(instance.getRegistration().getName()).append("** 服务已经");
            if(changedEvent.getStatusInfo().isOffline()){
                sb.append("Offline,请相关人员及时去救火 \n > ![screenshot](http://cdn.hiboot.cn/36fdcb749ffd4f7ab19b1c1daab4c9ef.jpg)");
            }else if(changedEvent.getStatusInfo().isDown()){
                sb.append("Down,请相关人员及时去救火 \n > ![screenshot](http://cdn.hiboot.cn/36fdcb749ffd4f7ab19b1c1daab4c9ef.jpg)");
            }else if(changedEvent.getStatusInfo().isUp()){
                sb.append("又重新复活了 \n > ![screenshot](http://cdn.hiboot.cn/cb967d6e0d634fc18d684e87153791e8.jpg)");
            }
            if(this.atMobiles != null){
                sb.append(this.getAtMobilesString(this.atMobiles));
            }
            msg = sb.toString();
        }
        Map<String, Object> messageJson = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        Map<String, Object> at = new HashMap<>();
        params.put("text", msg);
        params.put("title", this.title);
        at.put("atMobiles", StringUtils.commaDelimitedListToStringArray(this.atMobiles));
        at.put("isAtAll",false);
        messageJson.put("at", at);
        messageJson.put("msgtype", this.msgtype);
        messageJson.put(this.msgtype, params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(messageJson, headers);
    }

    private String getAtMobilesString(String s) {
        StringBuilder atMobiles = new StringBuilder();
        String[] mobiles = s.split(",");
        for (String mobile : mobiles) {
            atMobiles.append("@").append(mobile);
        }
        return atMobiles.toString();
    }

}
