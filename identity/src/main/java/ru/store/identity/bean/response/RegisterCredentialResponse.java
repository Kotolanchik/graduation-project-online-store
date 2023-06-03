package ru.store.identity.bean.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterCredentialResponse {
    String username;

    @Override
    public String toString() {
        return String.format("A user with the nickname %s has been successfully registered", username);
    }
}
