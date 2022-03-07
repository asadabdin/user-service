package org.company.user.service.homework.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.github.model.MinimalRepository;
import org.company.user.service.homework.client.GithubReposClient;
import org.company.user.service.homework.domain.User;
import org.company.user.service.homework.error.UserGithubUrlNotFoundException;
import org.company.user.service.homework.error.UserNotFoundException;
import org.company.user.service.homework.reporsitory.UserRepository;
import org.company.user.service.model.UserReposDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.company.user.service.homework.util.BeanUtils.copyProperties;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserRepoService {

    private final static Integer PAGE_SIZE = 100;

    private final UserRepository userRepository;
    private final GithubReposClient githubReposClient;

    public List<UserReposDTO> getUserReposDtos(@NonNull Integer id) {
        return userRepository.findById(id)
                .map(User::getGitHubUrl)
                .map(this::findUserName)
                .map(this::getAllRepos)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found by id %s", id)));
    }

    private List<UserReposDTO> getAllRepos(@NonNull String username) {
        List<UserReposDTO> userReposDTOS = new ArrayList<>();
        List<MinimalRepository> repositories;
        Integer page = 1;
        do {
            log.info("Looking for github repo for username [{}] and page [{}]", username, page);
            repositories = githubReposClient.reposListForUser(username, PAGE_SIZE, page++);
            userReposDTOS.addAll(
                    repositories
                            .stream()
                            .map(minimalRepository -> getUserReposDTO(username, minimalRepository))
                            .collect(Collectors.toList())
            );
        } while(repositories.size() == PAGE_SIZE);
        return userReposDTOS;
    }

    private UserReposDTO getUserReposDTO(String username, MinimalRepository minimalRepository) {
        Map<String, Integer> language = githubReposClient.reposListLanguages(username, minimalRepository.getName());
        UserReposDTO userReposDTO = copyProperties(minimalRepository, UserReposDTO.class);
        userReposDTO.setLanguage(language);
        return userReposDTO;
    }

    private String findUserName(String githubUrl) {
        return ofNullable(githubUrl)
                .map(url -> url.substring(url.lastIndexOf("/") + 1))
                .orElseThrow(() -> new UserGithubUrlNotFoundException(String.format("User github url not found by id %s", githubUrl)));
    }
}
