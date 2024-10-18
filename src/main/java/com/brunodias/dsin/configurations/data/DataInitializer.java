package com.brunodias.dsin.configurations.data;

import com.brunodias.dsin.entities.Role;
import com.brunodias.dsin.entities.User;
import com.brunodias.dsin.repositories.RoleRepository;
import com.brunodias.dsin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            User admin = User.builder()
                    .name("Leila Silva")
                    .phoneNumber("+55 11 91234-5678")
                    .email("leila@admin.com")
                    .roles(new HashSet<>())
                    .password(passwordEncoder.encode("@admin123")) // Criptografando a senha
                    .build();
            var adminRole = Role.builder().name("ROLE_ADMIN").build();
            admin.getRoles().add(adminRole);
            userRepository.save(admin);
        };
    }
}
