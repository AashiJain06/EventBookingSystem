package in.aj.main.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import in.aj.main.cleint.UserClient;
import in.aj.main.dto.AuthResponse;
import in.aj.main.dto.LoginRequest;
import in.aj.main.dto.UserDto;
import in.aj.main.security.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserClient userClient;

    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {

        UserDto user =
                userClient.getUserByEmail(request.getEmail());

        if(user == null) {
            throw new RuntimeException("User not found");
        }

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if(!matches) {
            throw new RuntimeException("Invalid password");
        }

        String token =
                jwtService.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}
