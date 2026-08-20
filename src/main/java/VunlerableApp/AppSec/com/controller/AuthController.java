package VunlerableApp.AppSec.com.controller;

import VunlerableApp.AppSec.com.dto.request.LoginRequest;
import VunlerableApp.AppSec.com.dto.request.RegisterRequest;
import VunlerableApp.AppSec.com.dto.response.ApiResponse;
import VunlerableApp.AppSec.com.dto.response.TokenResponse;
import VunlerableApp.AppSec.com.dto.response.UserResponse;
import VunlerableApp.AppSec.com.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody @Valid RegisterRequest request) {
        return ApiResponse.<UserResponse>builder()
                .data(authService.register(request))
                .build();
    }
    @PostMapping("/login")
    public ApiResponse<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
        return ApiResponse.<TokenResponse>builder()
                .data(authService.login(request))
                .build();
    }
    @PostMapping("/refresh")
    public ApiResponse<TokenResponse> refresh(@RequestParam String refreshToken) {
        return ApiResponse.<TokenResponse>builder()
                .data(authService.refresh(refreshToken))
                .build();
    }
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestParam String refreshToken) {
        authService.logout(refreshToken);
        return ApiResponse.<Void>builder()
                .message("Đăng xuất thành công")
                .build();
    }
}