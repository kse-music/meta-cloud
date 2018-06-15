package com.hiekn.metaboot.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("${custom.property.path}")
@ConfigurationProperties
public class Constants {

    public static String sseLocation;

    public static void setSseLocation(String sseLocation) {
        Constants.sseLocation = sseLocation;
    }

}