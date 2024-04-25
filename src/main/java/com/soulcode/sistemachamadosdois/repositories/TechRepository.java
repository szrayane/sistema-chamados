package com.soulcode.sistemachamadosdois.repositories;

import com.soulcode.sistemachamadosdois.model.TechnicianModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TechRepository extends JpaRepository<TechnicianModel, Long> {
    Optional<TechnicianModel> findByEmail(String email);
}
