package org.company.user.service.homework;

import org.company.user.service.homework.configuration.properties.BasicAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableFeignClients
@SpringBootApplication
@ComponentScan(basePackages = {"org.company"})
@EnableConfigurationProperties(BasicAuthProperties.class)
@EnableJpaAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class UserServiceHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceHomeworkApplication.class, args);
    }

}
