package dev.marvin.savings.appuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppUserService implements UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public AppUser saveAppUser(AppUser appUser) {
        var savedUser = appUserRepository.save(appUser);
        log.info("AppUser saved {}", savedUser);
        return savedUser;
    }

    public List<AppUser> getAllAppUsers() {
        return appUserRepository.findAll();
    }

    public boolean existsByUserName(String username) {
        return appUserRepository.existsByUsername(username);
    }

    public void setAppUserToEnabled(Boolean enabled, String username) {
        appUserRepository.setAppUserToEnabled(enabled, username);
    }
}
