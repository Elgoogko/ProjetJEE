package com.msfilm.msfilm;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MSFilmRestController {

	@GetMapping("/helloWorld")
	public String helloWorld() {
		System.out.println("Test 1 2");
		return "Hello world from Service A!";
	}

}