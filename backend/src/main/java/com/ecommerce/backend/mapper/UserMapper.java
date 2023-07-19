package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Role;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.payload.UserPatchRequestBody;
import com.ecommerce.backend.payload.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public User mapToUser(UserPostRequestBody userPostRequestBody) {
        if (validateUser(userPostRequestBody)) {
            User user = User.builder()
                    .email(userPostRequestBody.getEmail())
                    .name(userPostRequestBody.getName())
                    .birthDate(userPostRequestBody.getBirthDate())
                    .password(passwordEncoder.encode(userPostRequestBody.getPassword()))
                    .role(Role.ROLE_USER)
                    .accountCreationDate(LocalDate.now())
                    .build();
            return user;
        }
        return null;
    }

    public User mapToUserPatch(UserPatchRequestBody userPatchRequestBody) {
        User user = User.builder()
                .name(userPatchRequestBody.getName())
                .birthDate(userPatchRequestBody.getBirthDate())
                .build();
        return user;
    }

    public boolean validateUser(UserPostRequestBody userPostRequestBody) {
        if (userRepository.existsByEmail(userPostRequestBody.getEmail())) {
            throw new BadRequestException("Email já existente");
        }
        if (userPostRequestBody.getPassword() == null) {
            throw new BadRequestException("Senha não pode ser nula");
        }
        return true;
    }
}
