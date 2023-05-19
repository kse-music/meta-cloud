package com.hiekn.metacloud.monitor.ding;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "spring.boot.admin.notify.dingtalk", name = "webhook-url")
public class DingTalkNotifier extends de.codecentric.boot.admin.server.notify.DingTalkNotifier {

    private final DingTalkNotifierProperties dingTalkNotifierProperties;

    public DingTalkNotifier(InstanceRepository repository,DingTalkNotifierProperties dingTalkNotifierProperties) {
        super(repository,new RestTemplate());
        this.dingTalkNotifierProperties = dingTalkNotifierProperties;
    }

    @Override
    protected Object createMessage(InstanceEvent event, Instance instance) {
        String msg = "";
        String atMobiles = dingTalkNotifierProperties.getAtMobiles();
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
            sb.append(getAtMobilesString(atMobiles));
            msg = sb.toString();
        }
        Map<String, Object> messageJson = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        Map<String, Object> at = new HashMap<>();
        params.put("text", msg);
        String title = "服务通知";
        params.put("title", title);
        at.put("atMobiles", StringUtils.commaDelimitedListToStringArray(atMobiles));
        at.put("isAtAll",false);
        messageJson.put("at", at);
        String msgtype = "markdown";
        messageJson.put("msgtype", msgtype);
        messageJson.put(msgtype, params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(messageJson, headers);
    }

    private String getAtMobilesString(String atMobiles) {
        if(atMobiles == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String[] mobiles = atMobiles.split(",");
        for (String mobile : mobiles) {
            sb.append("@").append(mobile);
        }
        return sb.toString();
    }

}