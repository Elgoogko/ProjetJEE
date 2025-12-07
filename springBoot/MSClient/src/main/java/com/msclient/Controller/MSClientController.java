package com.msclient.Controller;

import com.msclient.Config.ClientSessionManager;
import com.msclient.Controller.entities.Client;
import com.msclient.Controller.entities.MainClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import jakarta.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/")
public class MSClientController {

    @Autowired
    private MainClient mainClient;
    
    @Autowired
    private ClientSessionManager clientSessionManager;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/")
    public String getIndex(HttpSession session, Model model) {
        // Récupère ou crée un client pour la session
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String username = auth.getName();
            Client client = clientSessionManager.getOrCreateClient(username, session);
            
            // Ajoute les informations au modèle
            model.addAttribute("client", client);
            model.addAttribute("username", username);
            model.addAttribute("clientCount", mainClient.getClientCount());
            model.addAttribute("allClients", mainClient.getAllClients());
            
            return "main"; // Page principale avec les informations
        }
        
        return "home"; // Page d'accueil pour les non connectés
    }
    
    @GetMapping("/main")
    public String getMainPage(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String username = auth.getName();
            Client client = clientSessionManager.getCurrentClient(session);
            
            if (client == null) {
                // Redirige vers la page d'accueil si aucun client n'est associé à la session
                return "redirect:/";
            }
            
            // Ajoute les informations au modèle
            model.addAttribute("client", client);
            model.addAttribute("username", username);
            model.addAttribute("clientCount", mainClient.getClientCount());
            model.addAttribute("allClients", mainClient.getAllClients());
            
            // Ici vous pouvez ajouter des données de la base de données
            // Par exemple, récupérer des informations de l'utilisateur depuis la BDD
            
            return "main";
        }
        
        return "redirect:/auth/login";
    }
    
    @RequestMapping("auth/login")
    public String getLogin() {
        return "auth/login";
    }

    @RequestMapping("auth/register")
    public String getRegisterPage() {
        return "auth/register";
    }
    
    @GetMapping("/client/info")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> getClientInfo(HttpSession session) {
        Client client = clientSessionManager.getCurrentClient(session);
        
        if (client != null) {
            return ResponseEntity.ok(Map.of(
                "clientId", client.getId(),
                "pseudo", client.getPseudo(),
                "role", client.getRole().name(),
                "totalClients", mainClient.getClientCount()
            ));
        }
        
        return ResponseEntity.ok(Map.of("message", "Aucun client connecté"));
    }
    
    @PostMapping("/receive")
    @ResponseBody
    public ResponseEntity<String> receiveMessage(@RequestBody MessageDTO message) {
        System.out.println("MSClient reçoit du MSFilm: " + message.getContent());
        return ResponseEntity.ok("Message reçu par MSClient: " + message.getContent());
    }
    
    @PostMapping("/send-to-film")
    @ResponseBody
    public ResponseEntity<String> sendToFilm(@RequestBody MessageDTO message) {
        String msFilmUrl = "http://msfilm/receive";
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            msFilmUrl, 
            message, 
            String.class
        );
        
        return ResponseEntity.ok("Message envoyé à MSFilm: " + message.getContent() + 
                                " - Réponse: " + response.getBody());
    }
}