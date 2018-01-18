package com.hiekn.metaboot.conf;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@ConditionalOnClass({Client.class})
@ConfigurationProperties("elasticsearch")
public class ElasticsearchConfig {

    private String[] host;
    private String clusterName;

    public void setHost(String[] host) {
        this.host = host;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }


    @Bean
    @ConditionalOnMissingBean
    public TransportClient transportClient(){
        Settings settings = Settings.builder().put("cluster.name", clusterName).build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        try {
            for (String host : host) {
                String[] ipPort = host.split(":");
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipPort[0]), Integer.valueOf(ipPort[1])));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return transportClient;
    }

}
