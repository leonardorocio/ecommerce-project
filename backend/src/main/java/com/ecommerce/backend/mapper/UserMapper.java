package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.payload.UserPatchRequestBody;
import com.ecommerce.backend.payload.UserPostRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
                    .birthDate(userPostRequestBody.getDate())
                    .password(passwordEncoder.encode(userPostRequestBody.getPassword()))
                    .cep(userPostRequestBody.getCep())
                    .build();
            return user;
        }
        return null;
    }

    public User mapToUserPatch(UserPatchRequestBody userPatchRequestBody) {
        User user = User.builder()
                .name(userPatchRequestBody.getName())
                .cep(userPatchRequestBody.getCep())
                .birthDate(userPatchRequestBody.getDate())
                .build();
        return user;
    }

    public boolean validateUser(UserPostRequestBody userPostRequestBody) {
        if (userRepository.existsByEmail(userPostRequestBody.getEmail())) {
            throw new BadRequestException("This email already exists");
        }
        if (userPostRequestBody.getPassword() == null) {
            throw new BadRequestException("Password cannot be null");
        }
        return true;
    }
}
