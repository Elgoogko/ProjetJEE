package com.msclient.Controller;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;

import com.DTO.CompressedFilmDTO;
import com.DTO.MovieDTO;
import com.msclient.Entity.User;
import com.msclient.Repository.UserRepository;



@Controller
@RequestMapping("/pages/")
public class PublicPagesController {

    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;

    @Autowired
    private UserRepository userRepository;

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
            ServiceInstance serviceInstance = discoveryClient.getInstances("msfilm").get(0);

            ResponseEntity<HashSet<CompressedFilmDTO>> response = restClient.get()
                    .uri(serviceInstance.getUri() +
                            "/api/getCompressedFilm?name=" + title + "&page="
                            + page)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<HashSet<CompressedFilmDTO>>() {
                    });

            // ---- Gestion des statuts ----
            if (response.getStatusCode().is5xxServerError()) {
                return "redirect:/error?type=server";
            }
            if (response.getStatusCode().value() == 422) {
                return "redirect:/error?type=unprocessable";
            }

            // ---- Réponse OK ----
            HashSet<CompressedFilmDTO> films = response.getBody();
            model.addAttribute("films", films);
            model.addAttribute("query", title);

            return "pages/search"; // Affiche la page
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?type=network";
        }
    }

    @GetMapping("movie")
    public String getMoviePage(@RequestParam String imdbID, Model model) {
        try {
            ServiceInstance serviceInstance = discoveryClient.getInstances("msfilm").get(0);

            ResponseEntity<MovieDTO> response = restClient.get()
                    .uri(serviceInstance.getUri() +
                            "/api/getMovieByID?imdbID=" + imdbID)
                    .retrieve()
                    .toEntity(new ParameterizedTypeReference<MovieDTO>() {
                    });

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

            return "pages/movie"; // Affiche la page
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error?type=network";
        }
    }

    @PostMapping("comment")
    public void postMethodName(@RequestParam String imdbID, @RequestParam String comment, @RequestParam float score, 
            @AuthenticationPrincipal org.springframework.security.core.userdetails.User springUser) {

        String username = springUser.getUsername(); // le login utilisé pour se connecter

        // si tu veux ton User JPA :
        User user = userRepository.findByUsername(username).orElseThrow();
        String email = user.getEmail();

    }
}
