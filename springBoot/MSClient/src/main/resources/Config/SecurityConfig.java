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
        //
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