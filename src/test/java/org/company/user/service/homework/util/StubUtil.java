package org.company.user.service.homework.util;

import com.github.tomakehurst.wiremock.WireMockServer;
import lombok.experimental.UtilityClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@UtilityClass
public class StubUtil {

    public static void stubGitHubRepo(WireMockServer mock, String username) {
        mock.stubFor(get(urlMatching("/users/" + username + "/repos\\?per_page=100&page=1"))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("json/github-repo.json"))
        );
    }

    public static void stubGitHubRepoLanguage(WireMockServer mock, String username) {
        mock.stubFor(get(urlMatching("/repos/" + username + "/" + "[A-Za-z0-9]+" + "/languages"))
                .willReturn(aResponse()
                        .withStatus(OK.value())
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                        .withBodyFile("json/repo-language.json"))
        );
    }

}
