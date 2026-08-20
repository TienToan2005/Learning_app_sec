package VunlerableApp.AppSec.com.service;

import VunlerableApp.AppSec.com.dto.request.LoginRequest;
import VunlerableApp.AppSec.com.dto.request.RegisterRequest;
import VunlerableApp.AppSec.com.dto.response.TokenResponse;
import VunlerableApp.AppSec.com.dto.response.UserResponse;
import VunlerableApp.AppSec.com.enums.ErrorCode;
import VunlerableApp.AppSec.com.enums.UserRole;
import VunlerableApp.AppSec.com.exception.AppException;
import VunlerableApp.AppSec.com.model.Token;
import VunlerableApp.AppSec.com.model.User;
import VunlerableApp.AppSec.com.respository.TokenRepository;
import VunlerableApp.AppSec.com.respository.UserRepository;
import VunlerableApp.AppSec.com.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public UserResponse register(RegisterRequest request){
        if(userRepository.findByUsername(request.username()).isPresent()){
            throw  new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }
        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(UserRole.USER);
        user.setAddress(request.address());
        user.setBirthday(request.birthday());

        User saved = userRepository.save(user);

        return new UserResponse(saved.getId(), saved.getUsername(), saved.getAddress(),saved.getBirthday(),saved.getRole());
    }
    public TokenResponse login(LoginRequest request){
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean matched = passwordEncoder.matches(request.password(), user.getPassword());
        if(!matched){
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        String accessToken = jwtService.generateToken(user);
        String refreshTokenValue = jwtService.generateRefreshToken();
        Token refreshToken = new Token();
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUser(user);
        refreshToken.setRevoked(false);
        refreshToken.setExpireDate(LocalDateTime.now().plusDays(7));

        return new TokenResponse(accessToken,refreshTokenValue);
    }
    public TokenResponse refresh(String refreshToken){
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));
        if(token.isRevoked()){
            throw new AppException(ErrorCode.TOKEN_REVOKED);
        }
        if(token.getExpireDate().isBefore(LocalDateTime.now())){
            throw new AppException(ErrorCode.TOKEN_EXPIRED);
        }

        User user = token.getUser();
        String newAccessToken = jwtService.generateToken(user);

        return new TokenResponse(newAccessToken,refreshToken);
    }
    public void logout(String refreshToken){
        Token token = tokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_TOKEN));

        token.setRevoked(true);
        tokenRepository.save(token);
    }
}
