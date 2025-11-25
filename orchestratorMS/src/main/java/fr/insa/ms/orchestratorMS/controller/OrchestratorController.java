package fr.insa.ms.orchestratorMS.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.orchestratorMS.model.Demande;
import fr.insa.ms.orchestratorMS.model.Student;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
	private final RestTemplate restTemplate;

	private static final String REQUEST_MS_BASE_URL = "http://requestMS/requests";
	private static final String RECOMMENDATION_MS_BASE_URL = "http://recommendationMS/recommendations";
	private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";

	public OrchestratorController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

//	@PutMapping("/{id}/tutor")
//	public ResponseEntity<?> isTutor(@PathVariable Integer id, @RequestParam Boolean isTutor) {
//
//		Student student = restTemplate.getForObject(STUDENT_MS_BASE_URL + "/" + id, Student.class);
//		
//		student.setEstTuteur(isTutor);
//		
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<Student> requestEntity = new HttpEntity<>(student, headers);
//
//		ResponseEntity<Student> response = restTemplate.exchange(
//		        STUDENT_MS_BASE_URL + "/" + id,   
//		        HttpMethod.PUT,                  
//		        requestEntity,                   
//		        Student.class                     
//		);
//		
//	    return ResponseEntity.ok(response.getBody());
//	}
//	
	@PostMapping("/demande")
	public ResponseEntity<?> createDemande(@RequestBody Demande newDemande) {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Demande> request = new HttpEntity<>(newDemande, headers);
		
		try {
			// Perform the call
			ResponseEntity<Demande> response = restTemplate.exchange(REQUEST_MS_BASE_URL, HttpMethod.POST, request,
					Demande.class);

			// Forward the successful response as-is
			return ResponseEntity.status(response.getStatusCode()).headers(response.getHeaders())
					.body(response.getBody());

		} catch (HttpStatusCodeException ex) {
			// This exception is thrown on 4xx or 5xx
			// Forward the status code, headers, and body as-is
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}
	}

}