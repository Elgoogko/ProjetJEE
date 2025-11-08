package com.msclient.msclient;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MSClientRestController {

	private final DiscoveryClient discoveryClient;
	private final RestClient restClient;

	public MSClientRestController(DiscoveryClient discoveryClient, RestClient.Builder restClientBuilder) {
		this.discoveryClient = discoveryClient;
		restClient = restClientBuilder.build();
	}

	@GetMapping("helloEureka")
	public String helloWorld() {
		ServiceInstance serviceInstance = discoveryClient.getInstances("msfilm").get(0);
		String msFIlmResponse = restClient.get()
				.uri(serviceInstance.getUri() + "/helloWorld")
				.retrieve()
				.body(String.class);
		return msFIlmResponse;
	}
}