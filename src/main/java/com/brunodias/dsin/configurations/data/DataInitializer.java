package com.brunodias.dsin.configurations.data;

import com.brunodias.dsin.entities.Role;
import com.brunodias.dsin.entities.Service;
import com.brunodias.dsin.entities.User;
import com.brunodias.dsin.repositories.RoleRepository;
import com.brunodias.dsin.repositories.ServiceRepository;
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
    private final ServiceRepository _serviceRepository;
    @Bean
    CommandLineRunner loadData() {
        return args -> {

            if (_serviceRepository.count() == 0) {
                Service corteCabeloFeminino = Service.builder()
                        .name("Corte de Cabelo Feminino")
                        .description("Corte de cabelo feminino com estilo e precisão.")
                        .price(70.0)
                        .build();

                Service corteCabeloMasculino = Service.builder()
                        .name("Corte de Cabelo Masculino")
                        .description("Corte de cabelo masculino moderno e estiloso.")
                        .price(50.0)
                        .build();

                Service manicure = Service.builder()
                        .name("Manicure")
                        .description("Manicure completa com esmaltação de qualidade.")
                        .price(30.0)
                        .build();

                Service pedicure = Service.builder()
                        .name("Pedicure")
                        .description("Pedicure relaxante com cuidados completos.")
                        .price(35.0)
                        .build();

                Service escovaModeladora = Service.builder()
                        .name("Escova Modeladora")
                        .description("Modelagem capilar com escova para um visual sofisticado.")
                        .price(50.0)
                        .build();

                Service hidratacaoCapilar = Service.builder()
                        .name("Hidratação Capilar")
                        .description("Tratamento de hidratação profunda para cabelos danificados.")
                        .price(90.0)
                        .build();

                Service depilacao = Service.builder()
                        .name("Depilação")
                        .description("Depilação corporal completa.")
                        .price(80.0)
                        .build();

                Service designSobrancelha = Service.builder()
                        .name("Design de Sobrancelhas")
                        .description("Design de sobrancelhas com pinça e modelagem.")
                        .price(25.0)
                        .build();

                Service maquiagem = Service.builder()
                        .name("Maquiagem")
                        .description("Maquiagem profissional para eventos.")
                        .price(120.0)
                        .build();

                _serviceRepository.save(corteCabeloFeminino);
                _serviceRepository.save(corteCabeloMasculino);
                _serviceRepository.save(manicure);
                _serviceRepository.save(pedicure);
                _serviceRepository.save(escovaModeladora);
                _serviceRepository.save(hidratacaoCapilar);
                _serviceRepository.save(depilacao);
                _serviceRepository.save(designSobrancelha);
                _serviceRepository.save(maquiagem);
            }

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
