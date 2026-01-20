// step 7 - 2factor class created
package com.eztrad.servercomp.model;

import com.eztrad.servercomp.domain.VerificationType;
import lombok.Data;

@Data
public class TwoFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
