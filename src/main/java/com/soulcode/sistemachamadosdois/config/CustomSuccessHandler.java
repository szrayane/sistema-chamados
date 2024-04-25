package com.soulcode.sistemachamadosdois.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        var authourities = authentication.getAuthorities();
        var roles = authourities.stream().map(GrantedAuthority::getAuthority).findFirst();

        if (roles.orElse("").equals("CLIENT")) {
            response.sendRedirect("/dashboard-cliente");
        } else if (roles.orElse("").equals("TECHNICIAN")) {
            response.sendRedirect("/dashboard-tecnico");
        }
    }

}
