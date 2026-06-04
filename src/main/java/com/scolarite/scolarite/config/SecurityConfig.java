package com.scolarite.scolarite.config;

import com.scolarite.scolarite.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Désactiver CSRF (API REST stateless)
            .csrf(csrf -> csrf.disable())

            // Pas de session HTTP (JWT = stateless)
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // Login public — pas besoin d'être authentifié
                .requestMatchers("/api/auth/**").permitAll()

                // GET publics — lecture libre
                .requestMatchers(HttpMethod.GET, "/api/**").permitAll()

                // Étudiants — ADMIN uniquement
                .requestMatchers(HttpMethod.POST,   "/api/etudiants").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/etudiants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/etudiants/**").hasRole("ADMIN")

                // Enseignants — ADMIN uniquement
                .requestMatchers(HttpMethod.POST,   "/api/enseignants").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/enseignants/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/enseignants/**").hasRole("ADMIN")

                // Modules — ADMIN uniquement
                .requestMatchers(HttpMethod.POST,   "/api/modules").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/modules/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/modules/**").hasRole("ADMIN")

                // Inscriptions — ADMIN uniquement
                .requestMatchers(HttpMethod.POST,   "/api/inscriptions/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/inscriptions/**").hasRole("ADMIN")

                // Notes — ADMIN uniquement
                .requestMatchers(HttpMethod.POST,   "/api/notes").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,    "/api/notes/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/notes/**").hasRole("ADMIN")

                // Tout le reste : authentifié
                .anyRequest().authenticated()
            )

            // Brancher le filtre JWT avant le filtre d'authentification Spring
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
