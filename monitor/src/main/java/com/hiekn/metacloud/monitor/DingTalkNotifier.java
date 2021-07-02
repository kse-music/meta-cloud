package com.hiekn.metacloud.monitor;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

@ConditionalOnProperty(
        prefix = "spring.boot.admin.notify.dingtalk",
        name = {"webhook-token"}
)
@Component
public class DingTalkNotifier extends AbstractStatusChangeNotifier {

    private final RestTemplate restTemplate = new RestTemplate();

    private final DingTalkNotifierConfiguration configuration;

    public DingTalkNotifier(DingTalkNotifierConfiguration configuration,InstanceRepository repository) {
        super(repository);
        this.configuration = configuration;
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> restTemplate.postForEntity(configuration.getWebhookToken(), configuration.create(event, instance),Void.class));
    }

}