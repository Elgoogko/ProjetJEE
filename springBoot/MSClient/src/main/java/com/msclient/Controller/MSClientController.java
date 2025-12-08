package com.msclient.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URI;
import java.util.HashSet;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClient;

import com.DTO.CompressedFilmDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/")
public class MSClientController {

	private final DiscoveryClient discoveryClient;
	private final RestClient restClient;

	public MSClientController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
		this.discoveryClient = discoveryClient;
		restClient = restClientBuilder.build();
	}

	@GetMapping("error")
	public String handleError(@RequestParam(required = false) String type, Model model) {
		String message;

		if ("server".equals(type)) {
			message = "Erreur interne du serveur (500).";
		} else if ("unprocessable".equals(type)) {
			message = "Requête non traitable (422). Vérifiez les paramètres envoyés.";
		} else if ("network".equals(type)) {
			message = "Erreur réseau ou microservice indisponible.";
		} else {
			message = "Une erreur inconnue est survenue.";
		}

		model.addAttribute("errorMessage", message);
		return "error"; // template Thymeleaf : pages/error.html
	}

	@RequestMapping("home")
	public String getIndex() {
		return "home";
	}

	@RequestMapping("auth/login")
	public String getLogin() {
		return "auth/login";
	}

	@RequestMapping("auth/register")
	public String getRegisterPage() {
		return "auth/register";
	}

	@GetMapping("pages/search")
	public String searchPageInitial(Model model) {
		model.addAttribute("films", List.of()); // liste vide
		return "pages/search";
	}

	@PostMapping("pages/search")
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
}