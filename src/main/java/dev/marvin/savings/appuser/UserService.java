package dev.marvin.savings.appuser;

import java.util.List;

public interface UserService {
    User createUser(UserRegistrationRequest userRegistrationRequest);
    List<User> getAllUsers();
//    void lockUserAccount();

//    void changePassword(String oldPassword, String newPassword);
}
