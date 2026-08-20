package VunlerableApp.AppSec.com.service;

import VunlerableApp.AppSec.com.dto.response.UserResponse;
import VunlerableApp.AppSec.com.enums.ErrorCode;
import VunlerableApp.AppSec.com.exception.AppException;
import VunlerableApp.AppSec.com.model.User;
import VunlerableApp.AppSec.com.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponse getUserById(Long id){
        User targetUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        String currentUsername  = SecurityContextHolder.getContext().getAuthentication().getName();

        boolean isOwner = targetUser.getUsername().equals(currentUsername);
        boolean isAdmin = SecurityContextHolder.getContext()
                .getAuthentication().getAuthorities().stream()
                .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_ADMIN"));

        if(!isOwner && !isAdmin){
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        return new UserResponse(targetUser.getId(),targetUser.getUsername(), targetUser.getAddress(), targetUser.getBirthday(),targetUser.getRole());
    }
}
