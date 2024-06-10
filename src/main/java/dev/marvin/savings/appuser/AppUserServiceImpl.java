package dev.marvin.savings.appuser;

import dev.marvin.savings.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AppUserServiceImpl implements UserDetailsService, AppUserService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public List<AppUserResponse> getAllAppUsers() {
        var users = appUserRepository.findAll();
        var response = new ArrayList<AppUserResponse>();
        users.forEach(appUser -> response.add(AppUserMapper.mapToDTO(appUser)));
        return response;
    }

    @Override
    public AppUserResponse getAppUserById(Integer id) {
        var user = appUserRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("user does not exist"));

        return AppUserMapper.mapToDTO(user);
    }

}
