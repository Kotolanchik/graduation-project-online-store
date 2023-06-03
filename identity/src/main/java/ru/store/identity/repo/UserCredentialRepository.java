package ru.store.identity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.store.identity.entity.UserCredential;

import java.util.Optional;

public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {
    Optional<UserCredential> findByUsername(String username);
    Optional<UserCredential> findByUsernameOrEmail(String username, String email);
}
