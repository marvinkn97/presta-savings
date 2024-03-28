package dev.marvin.savings.appuser;

import dev.marvin.savings.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserResponse createUser(User user) {
        User savedUser = userRepository.saveUser(user)
                .orElseThrow(() -> new GlobalException("failed to create user"));

        return mapEntityToDto(savedUser);
    }

    private UserResponse mapEntityToDto(User user) {
        return new UserResponse(
                user.getName(),
                user.getEmail(),
                user.getJoinDate(),
                user.isActive(),
                user.getRole().name()
        );
    }
}
