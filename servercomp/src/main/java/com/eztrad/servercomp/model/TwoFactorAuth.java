// step 7 - 2factor class created
package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;

}

// why not @Entity?
// Since this class will be marked with @Embedded in the User entity (Step 9),
// it's stored as columns within the user table, not as a separate entity.
// Adding @Entity would cause Spring to create a separate table and break the embedding.
