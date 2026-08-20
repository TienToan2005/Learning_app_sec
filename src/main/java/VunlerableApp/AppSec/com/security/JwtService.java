package VunlerableApp.AppSec.com.security;

import VunlerableApp.AppSec.com.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;


@Service
public class JwtService {
    @Value("${jwt.secret-key}")
    private  String secretKey;
    @Value("${jwt.expiration-key}")
    private long expiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(User user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role",user.getRole())
                .signWith(getSigningKey())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .compact();
    }
    public Claims parseToken(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public boolean isTokenValid(String token){
        try {
            parseToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getUsername(String token){
        return parseToken(token).getSubject();
    }
    public String getRole(String token){
        return parseToken(token).get("role", String.class);
    }
    public String generateRefreshToken(){
        return UUID.randomUUID().toString() + '-' + UUID.randomUUID().toString();
    }
}
