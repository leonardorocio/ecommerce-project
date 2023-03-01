package com.ecommerce.hardware.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;


@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {

    @Autowired
    private CustomAuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   RememberMeServices rememberMeServices) throws Exception {
        httpSecurity
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(
                        (requests) -> {
                            try {
                                requests
                                        .requestMatchers(HttpMethod.GET, "/products")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET, "/comments/products/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.POST, "/users")
                                        .permitAll()
                                        .anyRequest()
                                        .authenticated()
                                        .and()
                                        .httpBasic()
                                        .and()
                                        .csrf().disable();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .rememberMe()
                .key("GaticaBulicas")
                .tokenValiditySeconds(86400);
        return httpSecurity.build();
    }

    @Autowired
    public void setAuthenticationProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
