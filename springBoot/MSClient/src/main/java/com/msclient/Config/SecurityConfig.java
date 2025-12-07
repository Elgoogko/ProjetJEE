package com.msclient.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.http.HttpStatus;

import java.util.Arrays;


/**
 * Gestion de l'authentification et des autorisations web
 * Gestion des sessions utilisateur, connexion/déconnexion
 */

@EnableWebSecurity
@Configuration
public class SecurityConfig{

    @Autowired
    private Environment environment; //Permet d'accéder à la propriété liée aux profils actifs

    /**
     * Vérifie si un profil est actif dans les propriétés de l'application
     * @param profile - le nom du profil
     * @return - si un profil est actif
     */
    public boolean isProfileActive(String profile){
        return Arrays.asList(environment.getActiveProfiles()).contains(profile);
    }

    /**
     * Définition de la hiérarchie des rôles utilisateur
     * Les rôles supérieurs héritent des permissions des rôles inférieurs
     */
    @Bean
    public RoleHierarchy roleHierarchy(){
        return RoleHierarchyImpl.fromHierarchy("ROLE_DEV > ROLE_ADMIN > ROLE_USER");
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // Créez des utilisateurs en mémoire pour tester
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("password")) // Important: encoder le mot de passe
                .roles("USER")
                .build();
        
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin123"))
                .roles("ADMIN", "USER")
                .build();
        
        UserDetails test = User.builder()
                .username("test")
                .password(passwordEncoder().encode("test"))
                .roles("USER")
                .build();
        
        return new InMemoryUserDetailsManager(user, admin, test);
    }

    /**
     * Définition de :
     * - Quelles pages accessibles pour chaque type d'utilisateur
     * - Quelles pages nécessitent une authentification
     * - Comment les utilisateurs s'authentifient (connexion, déconnexion, chiffrement)
     * - Gestion de session utilisateur
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable()) // ESSENTIEL pour H2
        );

        http
                .csrf(csrf -> csrf
                        // Autoriser les endpoints de communication entre microservices SANS CSRF
                        .ignoringRequestMatchers(
                                "/auth/**",
                                "/h2/**",
                                "/receive",          // ← AJOUTÉ: Endpoint pour recevoir des messages
                                "/send-to-film",     // ← AJOUTÉ: Endpoint pour envoyer des messages
                                "/test",             // ← AJOUTÉ: Endpoint de test
                                "/ping",             // ← AJOUTÉ: Health check
                                "/api/**",           // ← AJOUTÉ: Tous les endpoints API
                                "/actuator/**"       // ← AJOUTÉ: Pour Eureka health checks
                        )
                )

                .authorizeHttpRequests(auth -> {
                    // Pages publiques (sans connexion)
                    auth.requestMatchers("/", "/auth/**").permitAll();
                    
                    // Endpoints de communication entre microservices (PUBLICS)
                    auth.requestMatchers(
                        "/receive",          // Recevoir des messages de MSFilm
                        "/send-to-film",     // Envoyer des messages à MSFilm
                        "/test",             // Test
                        "/ping",             // Health check simple
                        "/api/**"            // Tous les endpoints API
                    ).permitAll();
                    
                    // Actuator endpoints pour Eureka (PUBLICS)
                    auth.requestMatchers("/actuator/**").permitAll();
                    
                    // H2 Console (uniquement en développement)
                    //if (isProfileActive("dev") || isProfileActive("test")) {
                        auth.requestMatchers("/h2/**").permitAll();
                    //}
                    
                    // Pages dans /admin/ restreintes aux ADMIN et supérieurs
                    auth.requestMatchers("/admin/**").hasRole("ADMIN");
                    
                    // Toutes les autres requêtes nécessitent authentification
                    auth.anyRequest().authenticated();
                })
                
                // Configuration spéciale pour les requêtes API (microservices)
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        // Pour les requêtes API, retourner 401/403 au lieu de rediriger
                        .defaultAuthenticationEntryPointFor(
                                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                                request -> request.getHeader("Content-Type") != null && 
                                          request.getHeader("Content-Type").contains("application/json")
                        )
                        // Pour les pages web, rediriger normalement
                        .authenticationEntryPoint((request, response, authException) -> {
                            if (request.getHeader("Content-Type") != null && 
                                request.getHeader("Content-Type").contains("application/json")) {
                                // Requête API - retourner JSON
                                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                                response.setContentType("application/json");
                                response.getWriter().write("{\"error\":\"Unauthorized\",\"message\":\"Authentication required\"}");
                            } else {
                                // Page web - rediriger vers login
                                response.sendRedirect("/auth/login");
                            }
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            if (request.getHeader("Content-Type") != null && 
                                request.getHeader("Content-Type").contains("application/json")) {
                                // Requête API - retourner JSON
                                response.setStatus(HttpStatus.FORBIDDEN.value());
                                response.setContentType("application/json");
                                response.getWriter().write("{\"error\":\"Forbidden\",\"message\":\"Insufficient permissions\"}");
                            } else {
                                // Page web - rediriger vers error
                                response.sendRedirect("/error");
                            }
                        })
                )
                .formLogin(login -> login
                    .loginPage("/auth/login")
                    .loginProcessingUrl("/auth/login")  // IMPORTANT: URL de traitement du formulaire
                    //.usernameParameter("username")      // Nom du champ username
                    //.passwordParameter("password")      // Nom du champ password
                    .defaultSuccessUrl("/main", true)
                    .failureUrl("/auth/login?error=true")  // URL en cas d'échec
                    .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de déconnexion
                        .logoutSuccessUrl("/") // Redirige vers la page de base
                        .invalidateHttpSession(true) // Ferme la session
                        .deleteCookies("JSESSIONID") // Suppression des cookies de session
                )
                
                // Désactiver frame options pour les requêtes API
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Pour H2-Console
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'")
                        )
                );

        // Gestion de la session utilisateur
        http.sessionManagement(session -> session
                .invalidSessionUrl("/auth/login?expired=true") // Redirige à la page de connexion si l'utilisateur tente d'accéder à une page protégée avec une session invalide
                .maximumSessions(1) // Limitation d'une session par utilisateur
                .expiredUrl("/auth/login?session-expired=true") // Redirige à la page de connexion quand la session est expirée (timeout/logout)
                .maxSessionsPreventsLogin(false) // Permettre une nouvelle connexion si session expirée
        );

        return http.build();
    }


    /**
     * Utilisation de BCryptPasswordEncoder pour chiffrer les MDP avant le stockage en BDD
     * @return MDP chiffré
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}