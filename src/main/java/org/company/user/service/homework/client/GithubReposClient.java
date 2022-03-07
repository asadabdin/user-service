package org.company.user.service.homework.client;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.github.model.MinimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubReposClient {

    private final GithubClientApi githubClient;

    public List<MinimalRepository> reposListForUser(String username, Integer perPage, Integer page) {
        log.debug("calling third party api github to fetch repos for user [{}]", username);
        log.debug("fetching page [{}] of perPage [{}]", page, perPage);
        return githubClient.reposListForUser(username, null, null, null, perPage, page);
    }

    public Map<String, Integer> reposListLanguages(@NonNull String owner, @NonNull String repo) {
        log.debug("calling third party api github to fetch languages of repo [{}]", repo);
        return githubClient.reposListLanguages(owner, repo);
    }

}
