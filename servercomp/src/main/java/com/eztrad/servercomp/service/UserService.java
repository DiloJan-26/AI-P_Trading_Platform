package com.eztrad.servercomp.service;

import com.eztrad.servercomp.domain.VerificationType;
import com.eztrad.servercomp.model.User;

// Step 41 - user service created
public interface UserService {

    public User findUserProfileByJwt(String jwt) throws Exception;
    public User findUserByEmail(String email) throws Exception;
    public User findUserById(Long userId) throws Exception;

    public User enableTwoFactorAuthentication(
            VerificationType verificationType,
            String sendTo,
            User user);

    User updatePassword(User user, String newPassword);


}
