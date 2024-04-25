package com.soulcode.sistemachamadosdois.controller;

import com.soulcode.sistemachamadosdois.model.ClientModel;
import com.soulcode.sistemachamadosdois.model.TechnicianModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.service.ClientService;
import com.soulcode.sistemachamadosdois.service.TechService;
import com.soulcode.sistemachamadosdois.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;
    private final ClientService clientService;
    private final TechService techService;

    @GetMapping("/criar-chamado")
    public String returnPageCreateTicket(Model model) {
        model.addAttribute("ticket", new TicketModel());
        return "form-chamado";
    }

    @PostMapping("/criar-chamado")
    public String createTicket(@ModelAttribute("ticket") TicketModel ticket, RedirectAttributes redirectAttributes, @AuthenticationPrincipal UserDetails userDetails) {
        try {
            // pega o email do usuário autenticado e procura o usuário por ele
           ClientModel clientDb = clientService.getClientByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            // cria o ticket e salva o cliente dentro do ticket
            ticketService.createTicket(ticket, clientDb);
            return "redirect:/dashboard-cliente"; // Redireciona para a página de login
        } catch (Exception e) {
            // caso tenha algum erro
            redirectAttributes.addAttribute("error", true);
            return "redirect:/criar-chamado";
        }
    }

    @GetMapping("/editar-chamado/{ticketId}")
    public String showEditTicketForm(@PathVariable Long ticketId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Pega o ticket pelo id
        TicketModel ticket = ticketService.getTicketById(ticketId).orElseThrow(() -> new RuntimeException("Chamado não encontrado"));
        // Pega o nome do técnico para aparecer na tela
        TechnicianModel techDb = techService.getTechByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
        model.addAttribute("name", techDb.getName());
        // Adiciona o ticket ao modelo
        model.addAttribute("ticket", ticket);
        return "editar-chamado";
    }

    @PostMapping("editar-chamado/{ticketId}")
    public String updateTicket(@PathVariable Long ticketId, @ModelAttribute TicketModel ticket, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        TechnicianModel techDb = techService.getTechByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Técnico não encontrado"));
        // Adiciona técnico no chamado
        techService.updateTicketStatus(ticketId, ticket, techDb);
        return "redirect:/dashboard-tecnico/chamados-atribuidos";
    }

}
