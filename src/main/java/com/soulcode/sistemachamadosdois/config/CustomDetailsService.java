package com.soulcode.sistemachamadosdois.config;

import com.soulcode.sistemachamadosdois.model.UserModel;
import com.soulcode.sistemachamadosdois.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomDetailsService implements UserDetailsService {

    @Autowired
    private  UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));
        // Aqui criamos uma SimpleGrantedAuthority com o nome da role do usuário
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getName());
        System.out.println(user.getRole().getName());
        // Retornamos um UserDetails com as informações do usuário e sua autoridade (role)
        return new User(user.getEmail(), user.getPassword(), Collections.singleton(authority));
    }
}
