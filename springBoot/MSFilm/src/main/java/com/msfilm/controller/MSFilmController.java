package com.msfilm.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import com.actor.Actor;
import com.msfilm.controller.entities.Film;
import com.msfilm.msfilm.MainFilm;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Controller of type "Thymeleaf"
 * this controller only handles request on HTML pages such as looking at a movie or handle "404 pages error"
 * Or others MVC pages
 */
@Controller
public class MSFilmController {
    
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
            Actor filmAsActor = MainFilm.getInstance().searchFilm(name);
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
}