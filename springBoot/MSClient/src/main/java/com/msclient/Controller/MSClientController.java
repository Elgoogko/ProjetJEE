package com.msclient.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping("/")
public class MSClientController {

	@Autowired
    private RestTemplate restTemplate;


	private final DiscoveryClient discoveryClient;
	private final RestClient restClient;

	public MSClientController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
		this.discoveryClient = discoveryClient;
		restClient = restClientBuilder.build();
	}
/**
	@GetMapping("helloEureka")
	public String helloWorld() {
		ServiceInstance serviceInstance = discoveryClient.getInstances("msfilm").get(0);
		String msFIlmResponse = restClient.get()
				.uri(serviceInstance.getUri() + "/helloWorld")
				.retrieve()
				.body(String.class);
		return msFIlmResponse;
	}
 */

	@RequestMapping
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

	@PostMapping("/receive")
    public ResponseEntity<String> receiveMessage(@RequestBody MessageDTO message) {
        System.out.println("MSClient reçoit du MSFilm: " + message.getContent());
        return ResponseEntity.ok("Message reçu par MSClient: " + message.getContent());
    }
    
    // Endpoint pour envoyer un message à MSFilm
    @PostMapping("/send-to-film")
    public ResponseEntity<String> sendToFilm(@RequestBody MessageDTO message) {
        String msFilmUrl = "http://msfilm/receive";
        
        ResponseEntity<String> response = restTemplate.postForEntity(
            msFilmUrl, 
            message, 
            String.class
        );
        
        return ResponseEntity.ok("Message envoyé à MSFilm: " + message.getContent() + 
                                " - Réponse: " + response.getBody());
    }
}