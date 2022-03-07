package org.company.user.service.homework.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import com.github.fge.jackson.jsonpointer.JsonPointer;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.ReplaceOperation;
import lombok.SneakyThrows;
import org.company.user.service.homework.domain.User;
import org.company.user.service.homework.model.PageRequestDTO;
import org.company.user.service.homework.reporsitory.UserRepository;
import org.company.user.service.model.CreateUserDTO;
import org.company.user.service.model.SearchUserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.OffsetDateTime;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SuppressWarnings("FieldCanBeLocal")
@Sql(scripts = "/sql/users/insert-new-record.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/sql/delete-all.sql")
class UserControllerTest extends BaseControllerTest {

    private final String URL_GET = "/v1/users/{id}";
    private final String URL_PATCH = "/v1/users/{id}";
    private final String URL_DELETE = "/v1/users/{id}";
    private final String URL_SEARCH = "/v1/users/search";
    private final String URL_CREATE = "/v1/users";

    @Value("http://localhost:${local.server.port}")
    private String urlPrefix;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void test_userById_success() {
        //@formatter:off
        given()
                .with()
        .when()
                .get(urlPrefix + URL_GET, 1)
                .prettyPeek()
        .then()
                .statusCode(OK.value())
                .body("id", is(1))
                .body("firstname", is("ABC"))
                .body("surname", is("XYZ"))
                .body("position", is("Boss"))
                .body("gitHubUrl", is("https://github.com/ABC"))
                .body("createdOn", notNullValue())
                .body("updatedOn", notNullValue());
        //@formatter:on
    }

    @Test
    public void test_userById_FAILURE() {
        //@formatter:off
        given()
                .with()
        .when()
                .get(urlPrefix + URL_GET, 1987657)
                .prettyPeek()
        .then()
                .statusCode(NOT_FOUND.value());
        //@formatter:on
    }

    @Test
    public void test_create_success() {
        var createUserDto = new CreateUserDTO();
        createUserDto.setFirstname("ABC");
        createUserDto.setSurname("XYZ");
        createUserDto.setPosition("Boss");
        createUserDto.setGitHubUrl("xyz.com");

        //@formatter:off
        given()
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .with()
                .body(createUserDto)
        .when()
                .post(urlPrefix + URL_CREATE)
                .prettyPeek()
        .then()
                .statusCode(CREATED.value())
                .body("id", notNullValue())
                .body("firstname", is(createUserDto.getFirstname()))
                .body("surname", is(createUserDto.getSurname()))
                .body("position", is(createUserDto.getPosition()))
                .body("gitHubUrl", is(createUserDto.getGitHubUrl()))
                .body("createdOn", notNullValue())
                .body("updatedOn", notNullValue());
        //@formatter:on
    }

    @Test
    @SneakyThrows
    public void test_patch_success() {
        var firstName = "WER";
        var surname = "TRE";
        var position = "CTO";
        var gitHubUrl = "google.com";
        var patch = new JsonPatch(List.of(
                new ReplaceOperation(new JsonPointer("/firstname"), new TextNode(firstName)),
                new ReplaceOperation(new JsonPointer("/surname"), new TextNode(surname)),
                new ReplaceOperation(new JsonPointer("/position"), new TextNode(position)),
                new ReplaceOperation(new JsonPointer("/gitHubUrl"), new TextNode(gitHubUrl))
        ));
        var data = userRepository.findById(1);
        Assertions.assertTrue(data.isPresent());
        var userBeforePatch = data.get();
        //@formatter:off
        given()
                .header(HttpHeaders.CONTENT_TYPE, "application/json-patch+json")
                .with()
                .body(patch)
        .when()
                .patch(urlPrefix + URL_PATCH, 1)
                .prettyPeek()
        .then()
                .statusCode(OK.value())
                .body("id", notNullValue())
                .body("firstname", is(not(userBeforePatch.getFirstname())))
                .body("firstname", is(firstName))
                .body("surname", is(not(userBeforePatch.getSurname())))
                .body("surname", is(surname))
                .body("position", is(not(userBeforePatch.getPosition())))
                .body("position", is(position))
                .body("gitHubUrl", is(not(userBeforePatch.getGitHubUrl())))
                .body("gitHubUrl", is(gitHubUrl))
                .body("createdOn", notNullValue())
                .body("updatedOn", is(not(userBeforePatch.getUpdatedOn())))
                .body("updatedOn", notNullValue());
        //@formatter:on
    }

    @Test
    public void test_delete_success() {
        Assertions.assertTrue(userRepository.findById(1).isPresent());

        //@formatter:off
        given()
                .with()
        .when()
                .delete(urlPrefix + URL_DELETE, 1)
                .prettyPeek()
        .then()
                .statusCode(OK.value())
                .body(is("true"));
        //@formatter:on

        Assertions.assertFalse(userRepository.findById(1).isPresent());
    }

    @Test
    public void test_search_success() {
        var data = userRepository.findById(1);
        Assertions.assertTrue(data.isPresent());
        var userBeforePatch = data.get();
        var searchRequest = new SearchUserDTO();
        searchRequest.setFirstname(userBeforePatch.getFirstname());
        searchRequest.setSurname(userBeforePatch.getSurname());
        searchRequest.setPosition(userBeforePatch.getPosition());
        searchRequest.setGitHubUrl(userBeforePatch.getGitHubUrl());

        //@formatter:off
        given()
                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .with()
                .body(PageRequestDTO.builder().page(0).pageSize(10).criteria(searchRequest).build())
        .when()
                .post(urlPrefix + URL_SEARCH)
                .prettyPeek()
        .then()
                .statusCode(OK.value())
                .body("totalPages", is(1))
                .body("totalElements", is(1))
                .body("last", is(true))
                .body("content.id", hasItem(notNullValue()))
                .body("content.firstname", hasItem(searchRequest.getFirstname()))
                .body("content.surname", hasItem(searchRequest.getSurname()))
                .body("content.position", hasItem(searchRequest.getPosition()))
                .body("content.gitHubUrl", hasItem(searchRequest.getGitHubUrl()))
                .body("content.createdOn", hasItem(notNullValue()))
                .body("content.updatedOn", hasItem(notNullValue()));
        //@formatter:on
    }

}