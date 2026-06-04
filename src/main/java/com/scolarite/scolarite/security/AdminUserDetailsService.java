package com.scolarite.scolarite.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    public AdminUserDetailsService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Charge l'utilisateur par email (utilisé comme username dans JWT).
     * Un seul utilisateur ADMIN défini en dur.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if ("admin@scolarite.com".equals(email)) {
            return User.builder()
                    .username("admin@scolarite.com")
                    .password(passwordEncoder.encode("admin123"))
                    .roles("ADMIN")
                    .build();
        }
        throw new UsernameNotFoundException("Utilisateur introuvable : " + email);
    }
}
