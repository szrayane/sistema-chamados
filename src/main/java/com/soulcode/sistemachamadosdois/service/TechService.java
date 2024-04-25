package com.soulcode.sistemachamadosdois.service;

import com.soulcode.sistemachamadosdois.model.RoleModel;
import com.soulcode.sistemachamadosdois.model.TechnicianModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.repositories.RoleRepository;
import com.soulcode.sistemachamadosdois.repositories.TechRepository;
import com.soulcode.sistemachamadosdois.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TechService {

    private final RoleRepository roleRepository;
    private final TechRepository techRepository;
    private final TicketRepository ticketRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public TechnicianModel createTechnician(TechnicianModel tech) throws Exception {
        // pega a role de tech
        RoleModel role = roleRepository.findByName(RoleModel.Values.TECHNICIAN.name());
        // verifica se já existe o email
        Optional<TechnicianModel> techFromDb = techRepository.findByEmail(tech.getEmail());
        if (techFromDb.isPresent()) {
            throw new Exception("Já existe um técnico cadastrado com esse email.");
        }
        // salva a senha criptografada
        tech.setPassword(passwordEncoder.encode(tech.getPassword()));
        // salva a role de tech no usuário
        tech.setRole(role);
        tech.setActive(true);
        return techRepository.save(tech);
    }

    public Optional<TechnicianModel> getTechByEmail(String email) {
        // procura o tecnico pelo email
        return techRepository.findByEmail(email);
    }

    public List<TicketModel> getAvailableTickets() {
        // procurando os chamados que estão com técnico null
        return ticketRepository.findByTechnicianUserIdIsNull();
    }

    public List<TicketModel> getAssignedTickets(Long technicianId) {
        // procurando os chamados que estão atribuídos ao técnico logado
        return ticketRepository.findByTechnicianUserId(technicianId);
    }

    public void updateTicketStatus(Long ticketId, TicketModel updateTicket, TechnicianModel tech) {
        // procurando o chamado dentro do bd
        TicketModel ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new RuntimeException("Ticket não encontrado"));

        // atualizando o status, a prioridade e o técnico atribuído
        ticket.setStatus(updateTicket.getStatus());
        ticket.setPriority(updateTicket.getPriority());
        ticket.setTechnician(tech);

        // Salvando o chamado
        ticketRepository.save(ticket);
    }

}
