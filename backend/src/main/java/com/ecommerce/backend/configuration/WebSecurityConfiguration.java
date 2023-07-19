package com.ecommerce.backend.configuration;

import com.ecommerce.backend.services.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfiguration {

    private final JWTAuthEntryPoint authEntryPoint;

    private final JWTGenerator jwtGenerator;

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);
        httpSecurity.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(customExceptionHandlingFilter(), LogoutFilter.class);


        httpSecurity
                .authenticationProvider(authenticationProvider())
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity
                .authorizeHttpRequests()
                .requestMatchers("/users/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/error").permitAll()
                .requestMatchers("/swagger-ui").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/v3/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/comments/product/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/shipments").permitAll()
                .requestMatchers(HttpMethod.GET, "/shipper/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/products").permitAll()
                .requestMatchers(HttpMethod.GET, "/products/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/category").permitAll()
                .requestMatchers( "/products").hasRole("ADMIN")
                .requestMatchers("/category").hasRole("ADMIN")
                .requestMatchers("/shipper/**").hasRole("ADMIN")
                .requestMatchers("/shipments/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/order").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                .anyRequest().authenticated();

        httpSecurity
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public CustomExceptionHandling customExceptionHandlingFilter() {
        return new CustomExceptionHandling();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter(userDetailsService, jwtGenerator);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
