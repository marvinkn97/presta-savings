package dev.marvin.savings.appuser;

public class AppUserMapper {
    public static AppUserResponse mapToDTO(AppUser appUser){
        return AppUserResponse.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .role(appUser.getRole().name())
                .createdAt(appUser.getCreatedAt())
                .isEnabled(appUser.isEnabled())
                .isLocked(!appUser.isNotLocked())
                .build();
    }
}
