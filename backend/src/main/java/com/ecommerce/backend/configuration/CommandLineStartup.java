package com.ecommerce.backend.configuration;

import com.ecommerce.backend.models.Role;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@Log4j2
public class CommandLineStartup implements ApplicationListener<ApplicationReadyEvent> {

    private final UserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        boolean created = false;
        while (!created) {
            try {
                if (userRepository.count() == 0) {
                    User user = User.builder()
                            .name("Admin")
                            .email("admin.admin@admin.com")
                            .password(new BCryptPasswordEncoder().encode("admin123"))
                            .birthDate(LocalDate.now())
                            .role(Role.ROLE_ADMIN)
                            .build();
                    userRepository.save(user);
                    created = true;
                }
            } catch (Exception e) {
                log.info("Entidade ainda não foi criada, tentando novamente...");
            }
        }
    }
}
