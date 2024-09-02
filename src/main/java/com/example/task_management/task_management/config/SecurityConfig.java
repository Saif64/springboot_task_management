package com.example.task_management.task_management.config;

import com.example.task_management.task_management.utils.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public SecurityConfig(@Lazy JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder) {
        UserDetails superAdmin = User.builder().username("Saif").password(passwordEncoder.encode("test")) // Encode the password
                .roles("SUPER_ADMIN").build();

        UserDetails user = User.builder().username("Saif1").password(passwordEncoder.encode("test")) // Encode the password
                .roles("USER").build();

        return new InMemoryUserDetailsManager(superAdmin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(value -> value.requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyRole("SUPER_ADMIN", "USER").requestMatchers(HttpMethod.GET, "/api/tasks/**").hasAnyRole("SUPER_ADMIN", "USER").requestMatchers(HttpMethod.POST, "/api/tasks").hasAnyRole("SUPER_ADMIN").requestMatchers(HttpMethod.PUT, "/api/tasks").hasAnyRole("SUPER_ADMIN").requestMatchers(HttpMethod.DELETE, "/api/tasks").hasAnyRole("SUPER_ADMIN"));

        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
