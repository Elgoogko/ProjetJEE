package com.msclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.msclient.UserManager;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class MSClientController {

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

	@RequestMapping("auth/login")
	public String getLogin() {
		return "auth/login";
	}

	@RequestMapping("auth/register")
	public String getRegisterPage() {
		return "auth/register";
	}


}