package dev.marvin.savings.appuser;

import java.util.List;

public interface AppUserService {
    List<AppUserResponse> getAllAppUsers();
    AppUserResponse getAppUserById(Integer id);
}
