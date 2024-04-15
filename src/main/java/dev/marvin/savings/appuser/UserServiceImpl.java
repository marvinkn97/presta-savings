package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User createUser(UserRegistrationRequest registrationRequest) {

        User user = User.builder()
                .userName(registrationRequest.username())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .role(Role.valueOf(registrationRequest.role().toUpperCase()))
                .joinDate(LocalDateTime.now())
                .isActive(true)
                .isNotLocked(true)
                .build();

        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserResponse> response = new ArrayList<>();

        users.forEach(user -> {
            UserResponse userResponse = mapEntityToDto(user);
            response.add(userResponse);
        });

        return response;
    }


    private UserResponse mapEntityToDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getUserName(),
                user.getJoinDate(),
                user.isActive(),
                user.isNotLocked(),
                user.getRole().name()
        );
    }
}
