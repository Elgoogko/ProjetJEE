package com.msclient.Config;

import com.msclient.Controller.entities.MainClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class MainClientConfig {
    
    @Bean
    public MainClient mainClient() {
        System.out.println("Initialisation de MainClient au démarrage...");
        MainClient mainClient = new MainClient();
        // Vous pouvez initialiser des clients par défaut ici si nécessaire
        return mainClient;
    }
    
    @Bean
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ClientSessionManager clientSessionManager() {
        return new ClientSessionManager();
    }
}