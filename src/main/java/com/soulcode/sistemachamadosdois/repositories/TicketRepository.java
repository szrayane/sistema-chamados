package com.soulcode.sistemachamadosdois.repositories;

import com.soulcode.sistemachamadosdois.model.TicketModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TicketRepository extends JpaRepository<TicketModel, Long> {
    List<TicketModel> findByClientUserId(Long id);

    List<TicketModel> findByTechnicianUserIdIsNull();

    List<TicketModel> findByTechnicianUserId(Long technicianId);
}
