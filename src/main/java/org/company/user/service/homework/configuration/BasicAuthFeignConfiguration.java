package org.company.user.service.homework.configuration;

import feign.auth.BasicAuthRequestInterceptor;
import org.company.user.service.homework.configuration.properties.BasicAuthProperties;
import org.springframework.context.annotation.Bean;

public class BasicAuthFeignConfiguration {

    @Bean
    public BasicAuthRequestInterceptor requestInterceptor(final BasicAuthProperties properties) {
        return new BasicAuthRequestInterceptor(properties.getUserName(), properties.getPassword());
    }
}
