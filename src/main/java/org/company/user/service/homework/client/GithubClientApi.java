package org.company.user.service.homework.client;

import org.company.user.service.github.api.GithubReposApi;
import org.company.user.service.homework.configuration.BasicAuthFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = "github", configuration = BasicAuthFeignConfiguration.class, url = "${basic.auth.github.token-uri}")
public interface GithubClientApi extends GithubReposApi {

}
