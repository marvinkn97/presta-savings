
package dev.marvin.savings.service;

import dev.marvin.savings.dto.PasswordChangeRequest;
import dev.marvin.savings.dto.PasswordCreationRequest;
import dev.marvin.savings.dto.UserProfileRequest;
import dev.marvin.savings.dto.UserProfileUpdateRequest;
import dev.marvin.savings.entity.UserEntity;

public interface UserService {
    void registerMobile(String mobileNumber);

    void setPasswordForUser(PasswordCreationRequest passwordCreationRequest);

    Boolean isUserRegistered(String mobileNumber);

    void completeUserProfile(UserProfileRequest userProfileRequest);

    void changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest);

    void updateProfile(UserEntity userEntity, UserProfileUpdateRequest userProfileUpdateRequest);

}
