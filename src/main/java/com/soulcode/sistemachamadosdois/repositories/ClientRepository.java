package com.soulcode.sistemachamadosdois.repositories;

import com.soulcode.sistemachamadosdois.model.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientModel, Long> {
    Optional<ClientModel> findByEmail(String email);
}
