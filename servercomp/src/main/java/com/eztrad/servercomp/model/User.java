//step 4 - user class created

package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // step -6 - user role added
    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    // step-9 - 2factor
    @Embedded
    private TwoFactorAuth twoFactorAuth = new TwoFactorAuth();

}
