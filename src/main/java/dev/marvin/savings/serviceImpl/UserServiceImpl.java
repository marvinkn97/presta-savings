package dev.marvin.savings.serviceImpl;

import dev.marvin.constants.MessageConstants;
import dev.marvin.savings.dto.PasswordChangeRequest;
import dev.marvin.savings.dto.PasswordCreationRequest;
import dev.marvin.savings.dto.UserProfileRequest;
import dev.marvin.savings.dto.UserProfileUpdateRequest;
import dev.marvin.savings.entity.RoleEnum;
import dev.marvin.savings.entity.UserEntity;
import dev.marvin.savings.exception.DuplicateResourceException;
import dev.marvin.savings.exception.RequestValidationException;
import dev.marvin.savings.repository.UserRepository;
import dev.marvin.savings.service.UserService;
import dev.marvin.savings.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserUtils userUtils;

    @Override
    @Transactional
    public void registerMobile(String mobile) {
        log.info("Inside add method of UserServiceImpl");
        try {
            UserEntity user = new UserEntity();
            user.setMobileNumber(mobile);
            user.setRoleEnum(RoleEnum.ROLE_USER);
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            log.info("DataIntegrityViolationException: {}", e.getMessage(), e);
            if (e.getMessage().contains("mobile_number")) {
                throw new DuplicateResourceException(MessageConstants.DUPLICATE_MOBILE);
            }
        }
    }


    @Override
    public void setPasswordForUser(PasswordCreationRequest passwordCreationRequest) {
        log.info("Inside setPasswordForUser method of UserServiceImpl");
        UserEntity user = userUtils.getUser(passwordCreationRequest.mobile());
        if (!passwordCreationRequest.passwordMatch()) {
            throw new RequestValidationException("Password and confirm password do not match.");
        }

        String encodedPassword = passwordEncoder.encode(passwordCreationRequest.password());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public Boolean isUserRegistered(String mobileNumber) {
        log.info("Inside isUserRegistered method of UserServiceImpl");
        return userRepository.findByMobile(mobileNumber).isPresent();
    }

    @Override
    public void completeUserProfile(UserProfileRequest userProfileRequest) {
        log.info("Inside completeUserProfile method of UserServiceImpl");
        UserEntity user = userUtils.getUser(userProfileRequest.mobile());
        user.setFullName(userProfileRequest.name());
        user.setDateOfBirth(userProfileRequest.dateOfBirth());
        user.setIsFullyRegistered(userProfileRequest.termsAndConditions());
        userRepository.save(user);
    }

    @Override
    public void changePassword(UserEntity userEntity, PasswordChangeRequest passwordChangeRequest) {
        log.info("Inside changePassword method of UserServiceImpl ");

        if (ObjectUtils.isEmpty(userEntity) && ObjectUtils.isEmpty(passwordChangeRequest)) {
            return;
        }

        if (!passwordChangeRequest.isNewPasswordMatching()) {
            log.warn("New password and confirmation do not match");
            throw new RequestValidationException("new password and confirm password don't match");
        }

        if (!passwordChangeRequest.isNewPasswordSameAsOld()) {
            throw new RequestValidationException("New password cannot be the same as the old password.");
        }

        if (!passwordEncoder.matches(passwordChangeRequest.previousPassword(), userEntity.getPassword())) {
            log.warn("Previous password does not match stored password for user");
            throw new RequestValidationException("previous password does not match stored password");
        }

        userEntity.setPassword(passwordEncoder.encode(passwordChangeRequest.newPassword()));
        userRepository.save(userEntity);
        log.info("Password successfully changed");
    }

    @Override
    public void updateProfile(UserEntity userEntity, UserProfileUpdateRequest userProfileUpdateRequest) {
        log.info("Inside updateProfile method of UserServiceImpl ");
        if (ObjectUtils.isEmpty(userEntity) && ObjectUtils.isEmpty(userProfileUpdateRequest)) {
            return;
        }

        boolean changes = false;
        String name = userProfileUpdateRequest.name();

        if (StringUtils.hasText(name) && !Objects.equals(userEntity.getFullName(), name)) {
            userEntity.setFullName(name);
            changes = true;
        }

        if (!changes) {
            log.info("No changes detected for user");
            throw new RequestValidationException("no data changes detected");
        }

        userRepository.save(userEntity);
        log.info("Profile successfully updated for user");
    }
}