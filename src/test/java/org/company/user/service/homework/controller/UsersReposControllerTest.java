package org.company.user.service.homework.controller;

import org.company.user.service.homework.util.StubUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.jdbc.Sql;

import static io.restassured.RestAssured.given;
import static org.company.user.service.homework.util.StubUtil.stubGitHubRepo;
import static org.company.user.service.homework.util.StubUtil.stubGitHubRepoLanguage;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@Sql(scripts = "/sql/users/insert-new-record.sql")
@Sql(executionPhase = AFTER_TEST_METHOD, scripts = "/sql/delete-all.sql")
class UsersReposControllerTest extends BaseControllerTest {

    private final String URL_GET_REPOS = "/v1/users/{id}/repositories";

    @Value("http://localhost:${local.server.port}")
    private String urlPrefix;

    @Test
    public void test_userReposById_success() {
        stubGitHubRepo(wireMockServer, "ABC");
        stubGitHubRepoLanguage(wireMockServer, "ABC");
        //@formatter:off
        given()
                .with()
        .when()
                .get(urlPrefix + URL_GET_REPOS, 1)
                .prettyPeek()
        .then()
                .statusCode(OK.value())
                .body("id", hasItem(152861925))
                .body("name", hasItem("killbill"))
                .body("full_name", hasItem("abc/killbill"))
                .body("language", notNullValue());
        //@formatter:on
    }

}
