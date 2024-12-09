package dev.marvin.savings.serviceImpl;

import dev.marvin.savings.entity.UserEntity;
import dev.marvin.savings.entity.UserPrincipal;
import dev.marvin.savings.util.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserUtils userUtils;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
        log.info("Inside loadUserByUsername method of UserDetailsServiceImpl");
        UserEntity userEntity = userUtils.getUser(mobile);
        return new UserPrincipal(userEntity);
    }
}