package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("user with email [%s] not found".formatted(email)));

        var grantedAuthorities = user.getRole().grantedAuthorities();

        return User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
