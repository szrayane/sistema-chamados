package com.soulcode.sistemachamadosdois.service;

import com.soulcode.sistemachamadosdois.model.ClientModel;
import com.soulcode.sistemachamadosdois.model.RoleModel;
import com.soulcode.sistemachamadosdois.model.TicketModel;
import com.soulcode.sistemachamadosdois.repositories.ClientRepository;
import com.soulcode.sistemachamadosdois.repositories.RoleRepository;
import com.soulcode.sistemachamadosdois.repositories.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final RoleRepository roleRepository;
    private final TicketRepository ticketRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ClientModel createClient(ClientModel client) throws Exception {
        // buscando a role de cliente
      RoleModel role = roleRepository.findByName(RoleModel.Values.CLIENT.name());
      // verificando se já existe o email antes de cadastrar
      Optional<ClientModel> clientFromDb = clientRepository.findByEmail(client.getEmail());
        if (clientFromDb.isPresent()) {
            throw new Exception("Já existe um cliente cadastrado com esse email.");
        }
        // salvando a senha criptografada
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        // salvando a role dentro do cliente
        client.setRole(role);
        // salvando como true se o cliente é ativo
        client.setActive(true);
        return clientRepository.save(client);
    }

    public Optional<ClientModel> getClientByEmail(String email) {
        // procurando cliente pelo email
        return clientRepository.findByEmail(email);
    }

    public List<TicketModel> getTicketById(Long clientId) {
        // procurando tickets pelo id
        List<TicketModel> tickets = ticketRepository.findByClientUserId(clientId);
        return tickets;
    }

}
