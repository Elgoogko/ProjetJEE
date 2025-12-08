package com.msfilm.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller of type "Thymeleaf"
 * this controller only handles request on HTML pages such as looking at a movie
 * or handle "404 pages error"
 * Or others MVC pages
 */
@Controller
public class MSFilmController {

    /**
     * Index of microService
     * 
     * @return nothing
     */
    @RequestMapping("/")
    public String index() {
        return "movie";
    }
}