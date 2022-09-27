package com.example.demo.handler;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    SimpleUrlAuthenticationSuccessHandler managerSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/manager");
    SimpleUrlAuthenticationSuccessHandler customerSuccessHandler = new SimpleUrlAuthenticationSuccessHandler(
            "/customer");
    SimpleUrlAuthenticationSuccessHandler sellerSuccessHandler = new SimpleUrlAuthenticationSuccessHandler("/seller");

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (authorityName.equals("ROLE_MANAGER")) {
                this.managerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            } else if (authorityName.equals("ROLE_SELLER")) {
                this.sellerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
                return;
            }
            this.customerSuccessHandler.onAuthenticationSuccess(request, response, authentication);
        }
    }
}