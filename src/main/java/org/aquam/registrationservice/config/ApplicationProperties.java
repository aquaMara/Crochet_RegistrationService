package org.aquam.registrationservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApplicationProperties {

    @Value("${emailAPI.address}")
    private String EMAIL_API_BASE_URL;

    @Value("${registrationAPI.address}")
    private String REGISTRATION_API_BASE_URL;

    @Value("${expiration.time.hours}")
    private Long EXPIRATION_TIME_HOURS;

    @Value("${key}")
    private Long KEY;
}
