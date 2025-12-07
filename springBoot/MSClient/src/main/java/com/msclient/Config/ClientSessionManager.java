package com.msclient.Config;

import com.msclient.Controller.entities.Client;
import com.msclient.Controller.entities.MainClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession

;
@Component
public class ClientSessionManager {
    
    @Autowired
    private MainClient mainClient;
    
    /**
     * Crée ou récupère un client pour la session en cours
     */
    public Client getOrCreateClient(String pseudo, HttpSession session) {
        String sessionId = session.getId();
        
        // Vérifie si un client existe déjà pour cette session
        Client client = mainClient.getClientBySession(sessionId);
        
        if (client == null) {
            // Crée un nouveau client
            client = mainClient.createClient(pseudo, sessionId);
            System.out.println("Nouveau client créé pour la session: " + sessionId);
        }
        
        return client;
    }
    
    /**
     * Récupère le client de la session en cours
     */
    public Client getCurrentClient(HttpSession session) {
        String sessionId = session.getId();
        return mainClient.getClientBySession(sessionId);
    }
    
    /**
     * Supprime le client de la session en cours
     */
    public void removeCurrentClient(HttpSession session) {
        String sessionId = session.getId();
        mainClient.deleteClientBySession(sessionId);
    }
}