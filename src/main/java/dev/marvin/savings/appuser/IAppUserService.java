package dev.marvin.savings.appuser;

import java.util.List;

public interface IAppUserService {
    AppUser saveAppUser(AppUser appUser);

    List<AppUser> getAllAppUsers();

    boolean existsByUserName(String username);
}
