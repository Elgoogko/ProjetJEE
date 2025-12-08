package com.msclient.Controller;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.DTO.CompressedFilmDTO;
import com.DTO.MovieDTO;
import com.actors.MessageDTO;
import com.exceptions.ServicesException;
import com.msclient.UserManager;
import com.msclient.Entity.UserAsActor;

@Controller
@RequestMapping("/pages/")
public class PublicPagesController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @Autowired
    private UserManager userManager;

    public PublicPagesController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
        this.discoveryClient = discoveryClient;
        restClient = restClientBuilder.build();
    }

    @RequestMapping("home")
    public String getIndex() {
        return "pages/home";
    }

    @GetMapping("search")
    public String searchPageInitial(Model model) {
        model.addAttribute("films", List.of()); // liste vide
        return "pages/search";
    }

    @PostMapping("search")
    public String searchPage(@RequestParam String title, @RequestParam int page, Model model) {
        try {
            ServiceInstance filmInstance = discoveryClient.getInstances("msfilm").get(0);

            ResponseEntity<HashSet<CompressedFilmDTO>> response = restClient.get()
                    .uri(filmInstance.getUri() + "/api/getCompressedFilm?name=" + title + "&page=" + page)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<HashSet<CompressedFilmDTO>>() {
                    });

            if (response.getStatusCode().is5xxServerError()) {
                return "redirect:/error?type=server";
            }
            if (response.getStatusCode().value() == 422) {
                return "redirect:/error?type=unprocessable";
            }

            model.addAttribute("films", response.getBody());
            model.addAttribute("query", title);

            return "pages/search";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?type=network";
        }
    }

    @GetMapping("movie")
    public String getMoviePage(@RequestParam String imdbID, Model model, Principal principal) {
        try {
            ServiceInstance filmInstance = discoveryClient.getInstances("msfilm").get(0);

            ResponseEntity<MovieDTO> response = restClient.get()
                    .uri(filmInstance.getUri() +
                            "/api/getMovieByID?imdbID=" + imdbID)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<MovieDTO>() {
                    });

            ResponseEntity<MessageDTO> responseComments = restClient.get()
                    .uri(filmInstance.getUri() + "/api/getCommentsByFilm?filmId=" + imdbID)
                    .retrieve()
                    .toEntity(MessageDTO.class);

            MessageDTO dto = responseComments.getBody();

            // On récupère le JSON
            JSONArray comments = dto.message.getJSONArray("comments");

            // ---- Gestion des statuts ----
            if (response.getStatusCode().is5xxServerError()) {
                return "redirect:/error?type=server";
            }
            if (response.getStatusCode().value() == 422) {
                return "redirect:/error?type=unprocessable";
            }

            // ---- Réponse OK ----
            MovieDTO film = response.getBody();
            model.addAttribute("film", film);
            model.addAttribute("comments", comments);
            model.addAttribute("logged", principal != null); // True if connected, false otherwise
            return "pages/movie";

        } catch (ServicesException f) {
            f.printStackTrace();
            return "redirect:/error?type=server";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?type=network";
        }
    }

    @PostMapping("comment")
    public ResponseEntity<String> postComment(@RequestParam String comment, @RequestParam float score,
            @RequestParam String filmID, Principal principal) {
        String username = principal.getName();
        UserAsActor actor = (UserAsActor) userManager.getActorByID(username);

        if (actor == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Utilisateur non connecté ou acteur introuvable");
        }

        try {
            ServiceInstance filmInstance = discoveryClient.getInstances("msfilm").get(0);

            String filmServiceUrl = filmInstance.getUri().toString() + "/api/postComment";

            JSONObject json = new JSONObject();
            json.append("comment", comment);
            json.append("score", score);
            actor.send(json, filmID, filmServiceUrl);
            return ResponseEntity.ok("Commentaire envoyé !");
        } catch (ServicesException f) {
            f.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("MSFILM est injoignable " + f.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'envoi du commentaire : " + e.getMessage());
        }
    }
}
