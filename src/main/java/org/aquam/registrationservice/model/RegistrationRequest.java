package org.aquam.registrationservice.model;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@ToString
public class RegistrationRequest {
    @NotNull(message = "Username can not be null")
    private String username;
    @NotNull(message = "Password can not be null")
    private String password;
    @NotNull(message = "Email can not be null")
    @Email(message = "This email is not valid")
    private String email;
}
