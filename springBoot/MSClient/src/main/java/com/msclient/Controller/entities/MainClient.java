package com.msclient.Controller.entities;

import com.actor.Actor;
import java.util.HashMap;
import java.util.Map;

public class MainClient implements Actor {
    private Map<Integer, Client> clientsMap = new HashMap<>(); // Stocke les clients par ID
    private Map<String, Client> clientsBySession = new HashMap<>(); // Stocke les clients par session ID
    private static int nextClientId = 1; // ID unique pour chaque nouveau client
    private static int nextSessionCounter = 1;
    
    @Override
    public void sendWithActor(String s, Actor actor) {
        System.out.println("MainClient envoie : " + s + " à un acteur");
    }

    @Override
    public void sendWithId(String s, int id) {
        Client client = clientsMap.get(id);
        if (client != null) {
            System.out.println("Envoi de " + s + " au client ID: " + id);
            client.receiveWithId(s, this.getId());
        }
    }

    @Override
    public void receiveWithActor(String s, Actor actor) {
        System.out.println("MainClient reçoit de " + actor + ": " + s);
    }

    @Override
    public void receiveWithId(String s, int id) {
        System.out.println("MainClient reçoit du client " + id + ": " + s);
    }

    public int getId() {
        return 0; // ID 0 pour MainClient (parent)
    }

    /**
     * Crée un nouveau client et l'ajoute au MainClient
     */
    public Client createClient(String pseudo, String sessionId) {
        int clientId = nextClientId++;
        int numericSessionId = nextSessionCounter++;
        Client client = new Client(numericSessionId, clientId, pseudo, Role.USER);
        clientsMap.put(clientId, client);
        clientsBySession.put(sessionId, client);
        System.out.println("Nouveau client créé: ID=" + clientId + ", Pseudo=" + pseudo);
        return client;
    }

    /**
     * Récupère un client par son ID
     */
    public Client getClientById(int id) {
        return clientsMap.get(id);
    }

    /**
     * Récupère un client par son ID de session
     */
    public Client getClientBySession(String sessionId) {
        return clientsBySession.get(sessionId);
    }

    /**
     * Supprime un client par son ID
     */
    public boolean deleteClient(int id) {
        Client client = clientsMap.get(id);
        if (client != null) {
            clientsMap.remove(id);
            clientsBySession.remove(client.getIdSession());
            return true;
        }
        return false;
    }

    /**
     * Supprime un client par session ID
     */
    public boolean deleteClientBySession(String sessionId) {
        Client client = clientsBySession.get(sessionId);
        if (client != null) {
            clientsMap.remove(client.getId());
            clientsBySession.remove(sessionId);
            return true;
        }
        return false;
    }

    /**
     * Vérifie si un client existe
     */
    public boolean clientExists(int id) {
        return clientsMap.containsKey(id);
    }

    /**
     * Vérifie si une session existe
     */
    public boolean sessionExists(String sessionId) {
        return clientsBySession.containsKey(sessionId);
    }

    /**
     * Récupère tous les clients
     */
    public Map<Integer, Client> getAllClients() {
        return new HashMap<>(clientsMap);
    }

    /**
     * Récupère le nombre de clients connectés
     */
    public int getClientCount() {
        return clientsMap.size();
    }

    public boolean createNewSubActor() {
        return false;
    }

    public boolean deleteSubActor(int id) {
        return deleteClient(id);
    }

    public boolean thisSubActorExist(int id) {
        return clientExists(id);
    }
}