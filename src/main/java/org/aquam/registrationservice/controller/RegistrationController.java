package org.aquam.registrationservice.controller;

import lombok.RequiredArgsConstructor;
import org.aquam.registrationservice.model.RegistrationRequest;
import org.aquam.registrationservice.service.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/registration/")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        return new ResponseEntity<>(registrationService.register(registrationRequest), HttpStatus.CREATED);

    }
}
