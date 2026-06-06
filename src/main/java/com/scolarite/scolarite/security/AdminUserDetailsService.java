package com.scolarite.scolarite.security;

import jakarta.annotation.PostConstruct;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    // Mot de passe encodé une seule fois au démarrage
    private String encodedPassword;

    public AdminUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Encoder "admin123" une seule fois au démarrage de l'application
        this.encodedPassword = passwordEncoder.encode("admin123");
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if ("admin@scolarite.com".equals(email)) {
            return User.builder()
                    .username("admin@scolarite.com")
                    .password(encodedPassword)   // mot de passe déjà encodé
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("Utilisateur introuvable : " + email);
    }
}
