package com.soulcode.sistemachamadosdois.controller;

import com.soulcode.sistemachamadosdois.model.TechnicianModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.service.TechService;
import com.soulcode.sistemachamadosdois.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TechController {

    private final TechService techService;
    private final TicketService ticketService;

    @PostMapping("/cadastro-tecnico")
    @ResponseBody
    public TechnicianModel createTech(@RequestBody TechnicianModel tech) throws Exception {
        // criando o tecnico
        techService.createTechnician(tech);
        return tech;
    }

    @GetMapping("/dashboard-tecnico")
    public String showAvailableTickets(Model model, @AuthenticationPrincipal UserDetails userDetails){
        // procurando o técnico pelo email
        Optional<TechnicianModel> techDb = techService.getTechByEmail(userDetails.getUsername());
        if (techDb.isPresent()) {
            model.addAttribute("name", techDb.get().getName());
            List<TicketModel> tickets = techService.getAvailableTickets();
            // verifica se a lista de tickets é nula ou vazia
            if (tickets == null || tickets.isEmpty()) {
                // se não houver tickets, cria uma lista vazia
                tickets = new ArrayList<>();
            }
            // passando os tickets disponíveis para o modelo
            model.addAttribute("TicketsAvailable", tickets);
        }
        return "dashboard-tecnico";
    }

    @GetMapping("/dashboard-tecnico/chamados-atribuidos")
    public String showAssignedTickets(Model model, @AuthenticationPrincipal UserDetails userDetails){
        // Recupera o cliente da sessão
        Optional<TechnicianModel> techDb = techService.getTechByEmail(userDetails.getUsername());

        // Adiciona o técnico ao modelo
        model.addAttribute("Technician", techDb);

        // Adiciona o atributo name no html
        model.addAttribute("name", techDb.get().getName());

        // Recupera os tickets do técnico
        List<TicketModel> tickets = techService.getAssignedTickets(techDb.get().getUserId());

        // Verifica se a lista de tickets é nula ou vazia
        if (tickets == null || tickets.isEmpty()) {
            // Se não houver tickets, cria uma lista vazia
            tickets = new ArrayList<>();
        }

        // Adiciona os tickets ao modelo
        model.addAttribute("TicketsAssigned", tickets);

        return "dashboard-tecnico-atribuidos";
    }


}
