package ru.store.identity.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.store.identity.bean.request.RegisterUserCredentialRequest;
import ru.store.identity.bean.response.GenerateTokenResponse;
import ru.store.identity.bean.response.RegisterCredentialResponse;
import ru.store.identity.entity.UserCredential;
import ru.store.identity.repo.UserCredentialRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {
    UserCredentialRepository repository;
    PasswordEncoder encoder;
    JwtService jwtService;

    public RegisterCredentialResponse saveUser(final RegisterUserCredentialRequest credential) {
        if (repository.findByUsernameOrEmail(credential.getUsername(), credential.getEmail()).isPresent()) {
            throw new RuntimeException("user exist");
        }

        return RegisterCredentialResponse.builder()
                .username(repository.save(UserCredential.builder()
                        .email(credential.getEmail())
                        .username(credential.getUsername())
                        .password(encoder.encode(credential.getPassword()))
                        .build()
                ).getUsername())
                .build();
    }

    public GenerateTokenResponse generateToken(final String username) {
        return GenerateTokenResponse.builder()
                .token(jwtService.generateToken(username))
                .build();
    }

    public void validateToken(final String token) {
        jwtService.validateToken(token);
    }
}
