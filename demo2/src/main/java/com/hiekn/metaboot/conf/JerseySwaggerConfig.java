package com.hiekn.metaboot.conf;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.common.collect.Sets;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jersey.ResourceConfigCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.ClassUtils;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class JerseySwaggerConfig extends ResourceConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ResourceConfigCustomizer resourceRegister() {
        return config -> {
//            packages(environment.getProperty("base.package")).registerClasses(Sets.newHashSet(MultiPartFeature.class, JacksonJsonProvider.class));
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(Path.class));
            scanner.addIncludeFilter(new AnnotationTypeFilter(Provider.class));
            Set<Class<?>> collect = scanner.findCandidateComponents(environment.getProperty("base.package")).stream()
                    .map(beanDefinition -> ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.getClassLoader()))
                    .collect(Collectors.toSet());
            collect.addAll(Sets.newHashSet(MultiPartFeature.class, JacksonJsonProvider.class));
            registerClasses(collect);
        };
    }

    @Bean
    @ConditionalOnClass({ApiListingResource.class,SwaggerSerializers.class})
    @ConditionalOnProperty(prefix = "swagger",name = {"init"},havingValue = "true")
    public BeanConfig initSwagger(JerseySwaggerConfig jerseySwaggerConfig){
        jerseySwaggerConfig.registerClasses(Sets.newHashSet(ApiListingResource.class,SwaggerSerializers.class));
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion(environment.getProperty("swagger.version"));
        beanConfig.setTitle(environment.getProperty("swagger.title"));
        beanConfig.setHost(environment.getProperty("swagger.host"));
        beanConfig.setBasePath(environment.getProperty("swagger.basePath"));
        beanConfig.setResourcePackage(environment.getProperty("swagger.resourcePackage"));
        beanConfig.setScan();
        return beanConfig;
    }

}
