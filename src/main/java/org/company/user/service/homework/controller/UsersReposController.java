package org.company.user.service.homework.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.api.UsersReposApi;
import org.company.user.service.homework.service.UserRepoService;
import org.company.user.service.model.UserReposDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1"})
@Api(value = "user-service")
public class UsersReposController implements UsersReposApi {

    private final UserRepoService userRepoService;

    @Override
    public List<UserReposDTO> getUserReposById(Integer id) {
        log.info("looking for user repos by userId [{}]", id);
        return userRepoService.getUserReposDtos(id);
    }
}
