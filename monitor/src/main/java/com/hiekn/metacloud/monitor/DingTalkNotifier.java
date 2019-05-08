package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DingTalkNotifier extends AbstractStatusChangeNotifier {

    private static final String DEFAULT_MESSAGE = "*#{instance.registration.name}* (#{instance.id}) is *#{event.statusInfo.status}**";

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private RestTemplate restTemplate = new RestTemplate();
    private String webhookToken;
    private String atMobiles;
    private String msgtype = "markdown";
    private String title = "服务通知";
    private Expression message;

    public DingTalkNotifier(InstanceRepository repository) {
        super(repository);
        this.message = this.parser.parseExpression(DEFAULT_MESSAGE, ParserContext.TEMPLATE_EXPRESSION);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> restTemplate.postForEntity(webhookToken, createMessage(event, instance),Void.class));
    }

    private HttpEntity<Map<String, Object>> createMessage(InstanceEvent event,Instance instance) {
        String msg = "";
        if(event instanceof InstanceStatusChangedEvent){//只关心状态变化
            InstanceStatusChangedEvent changedEvent = (InstanceStatusChangedEvent) event;
            StringBuilder sb = new StringBuilder("\n >**");
            sb.append(instance.getRegistration().getName()).append("** 服务已经");
            if(changedEvent.getStatusInfo().isOffline()){
                sb.append("Offline,请相关人员及时去救火 \n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)");
            }else if(changedEvent.getStatusInfo().isDown()){
                sb.append("Down,请相关人员及时去救火 \n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)");
            }else if(changedEvent.getStatusInfo().isUp()){
                sb.append("又重新复活了 \n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)");
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
        at.put("atMobiles",StringUtils.commaDelimitedListToStringArray(this.atMobiles));
        at.put("isAtAll",false);
        messageJson.put("at", at);
        messageJson.put("msgtype", this.msgtype);
        messageJson.put(this.msgtype, params);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
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

    private String getMessage(InstanceEvent event, Instance instance) {
        Map<String, Object> root = new HashMap<>();
        root.put("event", event);
        root.put("instance", instance);
        root.put("lastStatus", getLastStatus(event.getInstance()));
        StandardEvaluationContext context = new StandardEvaluationContext(root);
        context.addPropertyAccessor(new MapAccessor());
        return message.getValue(context, String.class);
//        return this.atMobiles == null ? this.message.getValue(event, String.class) : this.message.getValue(event, String.class) + "\n >" + this.getAtMobilesString(this.atMobiles);
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getWebhookToken() {
        return webhookToken;
    }

    public void setWebhookToken(String webhookToken) {
        this.webhookToken = webhookToken;
    }

    public String getAtMobiles() {
        return atMobiles;
    }

    public void setAtMobiles(String atMobiles) {
        this.atMobiles = atMobiles;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public Expression getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = this.parser.parseExpression(message, ParserContext.TEMPLATE_EXPRESSION);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}