package in.aj.main.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import in.aj.main.dto.AuthResponse;
import in.aj.main.dto.LoginRequest;
import in.aj.main.service.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody LoginRequest request
    ) 
    {
        return authService.login(request);
    }
}
