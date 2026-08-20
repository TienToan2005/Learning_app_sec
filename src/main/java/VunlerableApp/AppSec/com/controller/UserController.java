package VunlerableApp.AppSec.com.controller;

import VunlerableApp.AppSec.com.dto.response.ApiResponse;
import VunlerableApp.AppSec.com.dto.response.UserResponse;
import VunlerableApp.AppSec.com.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id){
        return ApiResponse.<UserResponse>builder()
                .data(userService.getUserById(id))
                .build();
    }
}
