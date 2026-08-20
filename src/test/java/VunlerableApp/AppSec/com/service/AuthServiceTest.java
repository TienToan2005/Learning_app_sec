package VunlerableApp.AppSec.com.service;

import VunlerableApp.AppSec.com.dto.request.LoginRequest;
import VunlerableApp.AppSec.com.dto.response.TokenResponse;
import VunlerableApp.AppSec.com.enums.ErrorCode;
import VunlerableApp.AppSec.com.exception.AppException;
import VunlerableApp.AppSec.com.model.User;
import VunlerableApp.AppSec.com.respository.TokenRepository;
import VunlerableApp.AppSec.com.respository.UserRepository;
import VunlerableApp.AppSec.com.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void init(){
        user = new User();
        user.setId(1L);
        user.setUsername("alice");
        user.setPassword("hash_password123");
        user.setAddress("Ha Noi");
        user.setBirthday(LocalDate.of(2005,5,20));
    }

    @Test
    void loginSuccess_ReturnToken(){
        //GIVEN
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456","hash_password123")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("fake.jwt.token");
        when(jwtService.generateRefreshToken()).thenReturn("fake-refreshToken");

        //WHEN
        LoginRequest request = new LoginRequest("alice","123456");
        TokenResponse response = authService.login(request);

        //THEN
        assertNotNull(response);
        assertEquals("fake.jwt.token", response.getAccessToken());
        assertEquals("fake-refreshToken", response.getRefreshToken());
    }
    @Test
    void login_WrongPassword_ThrowException(){
        when(userRepository.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong_password","hash_password123")).thenReturn(false);

        LoginRequest request = new LoginRequest("alice","wrong_password");
        AppException exception = assertThrows(AppException.class,
                () -> authService.login(request));

        assertEquals(ErrorCode.WRONG_PASSWORD,exception.getErrorCode());
    }
    @Test
    void login_UserNotFound_ThrowException() {
        when(userRepository.findByUsername("ghost"))
                .thenReturn(Optional.empty());

        LoginRequest request = new LoginRequest("ghost", "123456");
        AppException exception = assertThrows(AppException.class,
                () -> authService.login(request));

        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
}
