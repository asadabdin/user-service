package org.company.user.service.homework.configuration.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("basic.auth.github")
public class BasicAuthProperties {

    private String tokenUri;
    private String userName;
    private String password;

}

