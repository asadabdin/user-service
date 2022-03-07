package org.company.user.service.homework.controller;

import com.github.fge.jsonpatch.JsonPatch;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.api.UsersApi;
import org.company.user.service.homework.model.PageRequestDTO;
import org.company.user.service.homework.service.UserService;
import org.company.user.service.model.CreateUserDTO;
import org.company.user.service.model.SearchUserDTO;
import org.company.user.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping({"/v1"})
@Api(value = "user-service")
public class UserController implements UsersApi {

    private final UserService userService;

    @Override
    public UserDTO getUserById(@NonNull Integer id) {
        log.info("Looking for user by id [{}]", id);
        return userService.findById(id);
    }

    @Override
    @ResponseStatus(CREATED)
    public UserDTO create(@Valid CreateUserDTO createUserDTO) {
        log.info("Creating user with request [{}]", createUserDTO);
        return userService.create(createUserDTO);
    }

    @Override
    public UserDTO patch(@NonNull Integer id, @Valid JsonPatch patch) {
        log.info("Patching user [{}] with request [{}]", id, patch);
        return userService.patch(id, patch);
    }

    @Override
    public Page<UserDTO> search(PageRequestDTO<SearchUserDTO> userPageRequestDTO) {
        log.info("Searching user with request [{}]", userPageRequestDTO);
        return userService.search(userPageRequestDTO);
    }

    @Override
    public Boolean deleteUserById(Integer id) {
        log.info("Deleting for user by id [{}]", id);
        return userService.deleteById(id);
    }
}
