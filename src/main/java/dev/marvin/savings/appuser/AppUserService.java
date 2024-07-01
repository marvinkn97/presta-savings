package dev.marvin.savings.appuser;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AppUserService {
    List<AppUserResponse> getAllAppUsers();
    AppUserResponse getAppUserById(Integer id);
    Page<AppUserResponse> getAllAppUsers_();
}
