package com.soulcode.sistemachamadosdois.controller;

import com.soulcode.sistemachamadosdois.model.ClientModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping("/cadastro-cliente")
    public String returnPageRegisterClient(Model model){
        model.addAttribute("client", new ClientModel());
       return "form-cliente";
    }

    @PostMapping("/cadastro-cliente")
    public String createClient(@ModelAttribute("client") ClientModel client, RedirectAttributes redirectAttributes) {
        try {
            // cria o cliente dentro do bd
            clientService.createClient(client);
            return "redirect:/login"; // Redireciona para a página de login
        } catch (Exception e) {
            // em caso de erro
            redirectAttributes.addAttribute("error", true);
            return "redirect:/cadastro-cliente";
        }
    }

    @GetMapping("/dashboard-cliente")
    public String showTicketsForClient(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Recupera o cliente da sessão
        ClientModel clientDb = clientService.getClientByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("Cliente não encontrado."));

        // Adiciona o cliente ao modelo
        model.addAttribute("client", clientDb);

        // Adiciona o atributo name no html
        model.addAttribute("name", clientDb.getName());

        // Recupera os tickets do cliente
        List<TicketModel> tickets = clientService.getTicketById(clientDb.getUserId());

        // Verifica se a lista de tickets é nula ou vazia
        if (tickets == null || tickets.isEmpty()) {
            // Se não houver tickets, cria uma lista vazia
            tickets = new ArrayList<>();
        }

        // Adiciona os tickets ao modelo
        model.addAttribute("tickets", tickets);

        return "dashboard-cliente";
    }

}
