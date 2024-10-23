package com.brunodias.dsin.repositories;

import com.brunodias.dsin.entities.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRepository extends JpaRepository<Service, UUID> {
    Optional<Service> findById(UUID id);
}
