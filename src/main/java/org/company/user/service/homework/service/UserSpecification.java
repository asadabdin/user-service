package org.company.user.service.homework.service;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.company.user.service.homework.domain.User;
import org.company.user.service.model.SearchUserDTO;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Optional.ofNullable;

@Slf4j
@UtilityClass
public class UserSpecification {

    public static Specification<User> get(SearchUserDTO searchRequest) {
        var spec = Specification.where(getDefaultSpec());

        ofNullable(searchRequest.getFirstname())
                .map(UserSpecification::getFirstnameSpec)
                .ifPresent(spec::and);

        ofNullable(searchRequest.getSurname())
                .map(UserSpecification::getSurnameSpec)
                .ifPresent(spec::and);

        ofNullable(searchRequest.getPosition())
                .map(UserSpecification::getPositionSpec)
                .ifPresent(spec::and);

        ofNullable(searchRequest.getGitHubUrl())
                .map(UserSpecification::getGitHubUrlSpec)
                .ifPresent(spec::and);

        log.debug("Composed specification is {}", spec);
        return spec;
    }

    private static Specification<User> getDefaultSpec() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNotNull(root.get("createdOn"));
    }

    private static Specification<User> getFirstnameSpec(String firstName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("firstname"), firstName);
    }

    private static Specification<User> getSurnameSpec(String surname) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("surname"), surname);
    }

    private static Specification<User> getPositionSpec(String position) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("position"), position);
    }

    private static Specification<User> getGitHubUrlSpec(String gitHubUrl) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("gitHubUrl"), gitHubUrl);
    }

}
