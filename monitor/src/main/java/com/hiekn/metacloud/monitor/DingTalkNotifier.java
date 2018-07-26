package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.event.ClientApplicationEvent;
import de.codecentric.boot.admin.event.ClientApplicationStatusChangedEvent;
import de.codecentric.boot.admin.notify.AbstractStatusChangeNotifier;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class DingTalkNotifier extends AbstractStatusChangeNotifier {

    private final SpelExpressionParser parser = new SpelExpressionParser();

    private RestTemplate restTemplate = new RestTemplate();
    private String webhookToken;
    private String atMobiles;
    private String msgtype = "markdown";
    private String title = "服务通知";
    private Expression message;

    public DingTalkNotifier() {
        this.message = this.parser.parseExpression("**#{application.name}** 已经从 #{from.status} 变成 **#{to.status}**", ParserContext.TEMPLATE_EXPRESSION);
    }

    @Override
    protected void doNotify(ClientApplicationEvent event) {
        if(event instanceof ClientApplicationStatusChangedEvent){//只关心状态变化
            ClientApplicationStatusChangedEvent changedEvent = (ClientApplicationStatusChangedEvent) event;
            StringBuilder sb = new StringBuilder("\n >**");
            sb.append(changedEvent.getApplication().getName()).append("** 服务已经");
            if(changedEvent.getTo().isOffline()){
                sb.append("跪了,请相关人员及时去救火 \n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)");
            }else if(changedEvent.getTo().isUp()){
                sb.append("又重新复活了 \n > ![screenshot](http://i01.lw.aliimg.com/media/lALPBbCc1ZhJGIvNAkzNBLA_1200_588.png)");
            }
            if(this.atMobiles != null){
                sb.append(this.getAtMobilesString(this.atMobiles));
            }
            this.restTemplate.postForEntity(this.webhookToken, this.createMessage(sb.toString()), Void.class);
        }
    }

    private HttpEntity<Map<String, Object>> createMessage(String msg) {
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

    private String getMessage(ClientApplicationEvent event) {
        return this.atMobiles == null ? this.message.getValue(event, String.class) : this.message.getValue(event, String.class) + "\n >" + this.getAtMobilesString(this.atMobiles);
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