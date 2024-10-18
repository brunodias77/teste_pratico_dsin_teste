package com.brunodias.dsin.data;
import com.brunodias.dsin.entities.User;
import com.brunodias.dsin.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserRepository _userRepository;

    @Bean
    CommandLineRunner loadData() {
        return args -> {
            _userRepository.save(new User("admin", "admin@example.com", "password", "ROLE_ADMIN"));
            _userRepository.save(new User("user", "user@example.com", "password", "ROLE_USER"));
        };
    }
}
