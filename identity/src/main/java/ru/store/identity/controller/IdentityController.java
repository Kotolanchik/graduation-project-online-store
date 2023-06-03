package ru.store.identity.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.store.identity.bean.request.AuthenticationRequest;
import ru.store.identity.bean.request.RegisterUserCredentialRequest;
import ru.store.identity.bean.response.GenerateTokenResponse;
import ru.store.identity.bean.response.RegisterCredentialResponse;
import ru.store.identity.service.IdentityService;

@RestController
@RequestMapping("/identity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class IdentityController {
    IdentityService service;
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public RegisterCredentialResponse createNewUser(@RequestBody final RegisterUserCredentialRequest request) {
        return service.saveUser(request);
    }

    @PostMapping("/token")
    public GenerateTokenResponse getToken(@RequestBody final AuthenticationRequest request) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        if (authenticate.isAuthenticated()) {
            return service.generateToken(request.getUsername());
        } else {
            throw new RuntimeException("invalid credential");
        }
    }

    @GetMapping("/validate")
    public String validateToken(final String token) {
        service.validateToken(token);
        return "token is valid";
    }
}
