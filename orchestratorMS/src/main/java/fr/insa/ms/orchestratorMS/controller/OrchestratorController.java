package fr.insa.ms.orchestratorMS.controller;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.orchestratorMS.model.ChooseTutorRequest;
import fr.insa.ms.orchestratorMS.model.CreateDemandeResponse;
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

	// creates a demande and returns a list of recommended tutors
	@PostMapping("/createRequest")
	public ResponseEntity<?> createRequest(@RequestBody Demande newDemande) {

		// POST call to create request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Demande> demandeRequest = new HttpEntity<>(newDemande, headers);

		try {
			ResponseEntity<Demande> responseDemandePost = restTemplate.exchange(REQUEST_MS_BASE_URL, HttpMethod.POST,
					demandeRequest, Demande.class);

			// if successful POST call
			if (responseDemandePost.getStatusCode().is2xxSuccessful()) {

				// get list of recommended students
				ResponseEntity<List<Student>> responseRecGet = restTemplate.exchange(RECOMMENDATION_MS_BASE_URL,
						HttpMethod.POST, demandeRequest, new ParameterizedTypeReference<List<Student>>() {
						});

				newDemande.idDemande = responseDemandePost.getBody().idDemande;
				return ResponseEntity.status(responseRecGet.getStatusCode())
						.body(new CreateDemandeResponse(newDemande, responseRecGet.getBody()));
			}
			return ResponseEntity.status(responseDemandePost.getStatusCode()).headers(responseDemandePost.getHeaders())
					.body(responseDemandePost.getBody());

		} catch (HttpStatusCodeException ex) {
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}
	}

	@PutMapping("/chooseTutor")
	public ResponseEntity<?> chooseTutor(@RequestBody ChooseTutorRequest chooseTutorRequest) {

		try {
			ResponseEntity<Demande> responseDemandeGet = restTemplate.exchange(
					REQUEST_MS_BASE_URL + "/" + chooseTutorRequest.idDemande(), HttpMethod.GET, null, Demande.class);

			if (responseDemandeGet.getStatusCode().is2xxSuccessful()) {
				Demande demande = responseDemandeGet.getBody();

				if (demande.id_etudiant_demandeur != chooseTutorRequest.idHelpSeeker()) {
				    return ResponseEntity.badRequest().body("A student can only choose a tutor for a help request he made.");
				}
				if (!"Attente".equals(demande.statut)) {
				    return ResponseEntity.badRequest().body("A tutor can only be chosen for help request with status \"Attente\".");
				}
				if (demande.id_etudiant_tuteur != 0) {
				    return ResponseEntity.badRequest().body("A tutor can be chosen for a help request only if one does not yet exist.");
				}
				if (chooseTutorRequest.idTutor() == chooseTutorRequest.idHelpSeeker()) {
				    return ResponseEntity.badRequest().body("A student asking for help cannot be his own tutor.");
				}
				demande.id_etudiant_tuteur = chooseTutorRequest.idTutor();
				demande.statut = "En Cours";
				
				
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Demande> demandeEntity = new HttpEntity<>(demande, headers);

				ResponseEntity<Map> response = restTemplate.exchange(
				        REQUEST_MS_BASE_URL,
				        HttpMethod.PUT,
				        demandeEntity,
				        Map.class
				);

				return ResponseEntity.status(response.getStatusCode())
				        .body(response.getBody());
			}

			return ResponseEntity.status(responseDemandeGet.getStatusCode()).headers(responseDemandeGet.getHeaders())
					.body(responseDemandeGet.getBody());
			} catch (

		HttpStatusCodeException ex) {
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}

	}

}