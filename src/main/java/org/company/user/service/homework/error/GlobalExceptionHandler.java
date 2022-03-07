package org.company.user.service.homework.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Component
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void handleNotFound(UserNotFoundException e) {
        log.info("Encountered not found exception while processing request [{}]", NOT_FOUND, e);
    }

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(UserGithubUrlNotFoundException.class)
    public void handleNotFound(UserGithubUrlNotFoundException e) {
        log.info("Encountered not found exception while processing request [{}]", NOT_FOUND, e);
    }

}
