package com.brunodias.dsin.repositories;

import com.brunodias.dsin.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name); // Método para buscar uma role pelo nome

}
