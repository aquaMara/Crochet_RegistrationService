package org.aquam.registrationservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Value;

@Value
public class ConfirmationData {

    private String email;
    private String confirmationLink;

}
