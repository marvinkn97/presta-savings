package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    @Override
    public User createUser(User user) {
       return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
