package com.hiekn.metaboot.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;

@RefreshScope
@Configuration
@ConfigurationProperties
public class RemoteConfig {

    public String foo;

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        String a = "";
        try {
             a = new String(foo.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        this.foo = foo;
    }
}
