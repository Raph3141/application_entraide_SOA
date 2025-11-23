package fr.insa.ms.orchestratorMS.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
	 private final RestTemplate restTemplate;
	 
	 private static final String REQUEST_MS_BASE_URL = "http://requestMS/requests";
	 private static final String RECOMMENDATION_MS_BASE_URL = "http://recommendationMS/recommendation";
	 private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";
	 
	 public OrchestratorController(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	 
	 
}
