package org.aquam.registrationservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import javax.validation.ConstraintViolation;
import java.time.ZonedDateTime;
import java.util.Set;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class AppResponse {

    private final String message;
    private final ZonedDateTime zonedDateTime;
    private final HttpStatus httpStatus;
    private Set<ConstraintViolation<?>> validationExceptions;
}
