import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private boolean isProfileActive(String profile){
        //final String[] profiles = environment.getActiveProfiles();
        //return Arrays.asList(profiles).contains(profile);
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

    /**
     * Définition de :
     * - Quelles pages accessibles pour chaque type d'utilisateur
     * - Quelles pages nécessitent une authentification
     * - Comment les utilisateurs s'authentifient (connexion, déconnexion, chiffrement)
     * - Gestion de session utilisateur
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomLoginSuccessHandler loginSuccessHandler,
                                                   UserExistenceFilter userExistenceFilter) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers("/auth/**","/h2-console/**")) // Désactivation de la protection CSRF (nécessaire pour H2-Console)

                .addFilterBefore(userExistenceFilter, UsernamePasswordAuthenticationFilter.class) // Filtre la connexion des sessions en mémoire de comptes qui n'existent plus
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/","/login","/register").permitAll(); // Pages publiques (sans connexion)
                    auth.requestMatchers("/admin/**").hasRole("ADMIN"); // Pages dans /admin/ restreintes aux ADMIN et supérieurs

                    auth.anyRequest().authenticated(); // Toute les autres requêtes nécessitent authentification
                })
                .formLogin(login -> login // Configuration du login via un formulaire
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler) // Redirection vers l'accueil après authentification
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL de déconnexion
                        .logoutSuccessUrl("/") // Redirige vers la page de base
                        .invalidateHttpSession(true) // Ferme la session
                        .deleteCookies("JSESSIONID") // Suppression des cookies de session
                )
                .headers(headers -> headers.frameOptions((frameOptions) -> frameOptions.disable())); // Peut être nécessaire pour afficher correctement H2-Console

        // Gestion de la session utilisateur
        http.sessionManagement(session -> session
                .invalidSessionUrl("/login?expired=true") // Redirige à la page de connexion si l'utilisateur tente d'accéder à une page protégée avec une session invalide
                .maximumSessions(1) // Limitation de une session par utilisateur
                .expiredUrl("/login?session-expired=true") // Redirige à la page dee connexion quand la session est expirée (timeout/logout)
        );

        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/error") // Redirige les autres erreurs et accès aux pages protégées sans permissions à une page d'erreur
        );

        return http.build();

    }

    /**
     * Utilisation de BCryptPasswordEncoder pour chiffer les MDP avant le stockage en BDD
     * @return MDP chiffré
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}