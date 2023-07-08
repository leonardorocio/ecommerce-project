package com.ecommerce.backend.configuration;

import com.ecommerce.backend.models.Role;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommandLineStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (userRepository.count() == 0) {
            User user = User.builder()
                    .name("Admin")
                    .email("admin.admin@admin.com")
                    .password(new BCryptPasswordEncoder().encode("admin123"))
                    .birthDate(LocalDate.now())
                    .role(Role.ROLE_ADMIN)
                    .build();
            userRepository.save(user);
        }
    }
}
