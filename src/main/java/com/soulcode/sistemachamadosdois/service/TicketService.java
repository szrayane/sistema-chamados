package com.soulcode.sistemachamadosdois.service;

import com.soulcode.sistemachamadosdois.model.ClientModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;

    public TicketModel createTicket(TicketModel ticket, ClientModel client){

        // setando o cliente dentro do ticket
        ticket.setClient(client);
        // setando o status no ticket
        ticket.setStatus(TicketModel.TicketStatus.OPEN);
        // setando o status dentro do ticket
        ticket.setPriority(TicketModel.Priority.AGUARDANDO);
        return ticketRepository.save(ticket);
    }

    public Optional<TicketModel> getTicketById(Long ticketId){
        // procurando o ticket por id
        return ticketRepository.findById(ticketId);
    }

}
