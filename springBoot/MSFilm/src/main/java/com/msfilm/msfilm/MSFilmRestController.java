package com.msfilm.msfilm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.msfilm.film.Film;

@RestController
@RequestMapping("/api/Films")
public class MSFilmRestController {

	MainFilm mainFilm = MainFilm.getInstance();

	@GetMapping("/getMovie/{name}")
	public Film getSingleMovie(@PathVariable String name) {
		Film f = mainFilm.searchFilm(name);
		return f;
	}

	@GetMapping("/test/{num}")
	public int getMethodName(@PathVariable int num) {
		return num;
	}
}