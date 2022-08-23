package org.aquam.registrationservice.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApplicationProperties {

    @Value("${emailAPI.address}")
    private String emailApiBaseUrl;

    @Value("${registrationAPI.address}")
    private String registrationApiBaseUrl;

    @Value("${expiration.time.hours}")
    private Long expirationTimeInHours;

    @Value("${key}")
    private Long key;
}
