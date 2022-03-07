package org.company.user.service.homework.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.homework.domain.User;
import org.company.user.service.homework.error.UserNotFoundException;
import org.company.user.service.homework.model.PageRequestDTO;
import org.company.user.service.homework.reporsitory.UserRepository;
import org.company.user.service.homework.util.BeanUtils;
import org.company.user.service.model.CreateUserDTO;
import org.company.user.service.model.SearchUserDTO;
import org.company.user.service.model.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import static java.util.Optional.of;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public UserDTO findById(@NonNull Integer id) {
        return userRepository.findById(id)
                .map(this::convert)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found by id %s", id)));
    }

    public UserDTO create(@NonNull CreateUserDTO createUserDTO) {
        var user = BeanUtils.copyProperties(createUserDTO, User.class);
        user = userRepository.save(user);
        var userDto = convert(user);
        log.info("User has created [{}]", userDto);
        return userDto;
    }

    public UserDTO patch(@NonNull Integer id, @NonNull JsonPatch patch) {
        return userRepository.findById(id)
                .map(user -> applyPatchToUser(patch, user))
                .map(userRepository::save)
                .map(this::convert)
                .orElseThrow(() -> new UserNotFoundException(String.format("User not found by id %s", id)));
    }

    @SneakyThrows
    private User applyPatchToUser(@NonNull JsonPatch patch, @NonNull User user) {
        JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
        return objectMapper.treeToValue(patched, User.class);
    }

    public Page<UserDTO> search(@NonNull PageRequestDTO<SearchUserDTO> userPageRequestDTO) {
        var pageable = PageRequest.of(userPageRequestDTO.getPage(), userPageRequestDTO.getPageSize());
        return of(userPageRequestDTO.getCriteria())
                .map(UserSpecification::get)
                .map(userSpecification -> userRepository.findAll(userSpecification, pageable))
                .map(users -> users.map(this::convert))
                .orElseGet(() -> Page.empty(pageable));
    }

    private UserDTO convert(User user) {
        return BeanUtils.copyProperties(user, UserDTO.class);
    }

    public Boolean deleteById(@NonNull Integer id) {
        userRepository.deleteById(id);
        return true;
    }
}
