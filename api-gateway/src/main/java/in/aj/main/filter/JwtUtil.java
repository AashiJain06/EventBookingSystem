package in.aj.main.filter;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    // Validate token
    public void validateToken(String token) {

        Key key =
                Keys.hmacShaKeyFor(secret.getBytes());

        Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    // Extract role
    public String extractRole(String token) {

        Key key =
                Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        return claims.get("role", String.class);
    }

    // Extract email
    public String extractEmail(String token) {

        Key key =
                Keys.hmacShaKeyFor(secret.getBytes());

        Claims claims =
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

        return claims.getSubject();
    }
}