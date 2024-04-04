package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public User createUser(UserRegistrationRequest registrationRequest) {

        User user = User.builder()
                .name(registrationRequest.name())
                .email(registrationRequest.email())
                .password(passwordEncoder.encode(registrationRequest.password()))
                .role(Role.valueOf(registrationRequest.role().toUpperCase()))
                .joinDate(System.currentTimeMillis())
                .isActive(true)
                .isNotLocked(true)
                .build();

       return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
