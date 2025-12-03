package com.msfilm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import com.actors.Actor;
import com.msfilm.controller.Managers.MainFilmManager;
import com.msfilm.controller.entities.Film;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * Controller of type "Thymeleaf"
 * this controller only handles request on HTML pages such as looking at a movie or handle "404 pages error"
 * Or others MVC pages
 */
@Controller
@RestController
public class MSFilmController {

    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private MainFilmManager filmManager;
    /**
     * Index of microService
     * @return nothing 
     */
    @RequestMapping("/")
	public String index() {
        return "movie";
    }

    /**
     * Return the page of the movie according to the title
     * If the Title is not exact, it  will return an error page
     * @param name exact name of the movie
     * @param model MCV 
     * @return film.html if found, else 404
     */
	@RequestMapping("/movie")
	public String moviePage(@RequestParam String name, Model model) {
		try {
            Actor filmAsActor = filmManager.getExactFilm(name);
			Film film = (Film) filmAsActor;

            model.addAttribute("filmAsActor", filmAsActor);
			model.addAttribute("film", film);
            model.addAttribute("success", true);
        } catch (Exception e) {

            model.addAttribute("error", "Erreur : " + e.getMessage());
            model.addAttribute("success", false);
        }
        return "film";
    }

    @PostMapping("/receive")
    public ResponseEntity<String> receiveMessage(@RequestBody MessageDTO message) {
        System.out.println("MSFilm reçoit du MSClient: " + message.getContent());
        return ResponseEntity.ok("Message reçu par MSFilm: " + message.getContent());
    }

    @PostMapping("/send-to-client")
    public ResponseEntity<String> sendToClient(@RequestBody MessageDTO message) {
        String msClientUrl = "http://MSCLIENT/api/client/receive";
        
        // Envoi du message à MSClient via Eureka
        ResponseEntity<String> response = restTemplate.postForEntity(
            msClientUrl, 
            message, 
            String.class
        );
        
        return ResponseEntity.ok("Message envoyé à MSClient: " + message.getContent() + 
                                " - Réponse: " + response.getBody());
    }
}