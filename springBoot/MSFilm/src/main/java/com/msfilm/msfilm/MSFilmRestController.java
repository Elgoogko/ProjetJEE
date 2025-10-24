package com.msfilm.msfilm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.actor.*;

@RestController
public class MSFilmRestController {

	@GetMapping("/helloWorld")
	public String helloWorld() {

		return "Hello world from Service A!";
	}

}