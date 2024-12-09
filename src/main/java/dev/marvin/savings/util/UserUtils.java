
package dev.marvin.savings.util;

import dev.marvin.constants.MessageConstants;
import dev.marvin.savings.entity.UserEntity;
import dev.marvin.savings.exception.ResourceNotFoundException;
import dev.marvin.savings.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;

    public UserEntity getUser(String mobile) {
        return userRepository.findByMobile(mobile)
                .orElseThrow(() -> new ResourceNotFoundException(MessageConstants.USER_NOT_FOUND));
    }
}
