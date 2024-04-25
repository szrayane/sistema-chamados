package com.soulcode.sistemachamadosdois.repositories;

import com.soulcode.sistemachamadosdois.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleModel, Long> {
    RoleModel findByName(String name);
}
