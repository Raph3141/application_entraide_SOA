package fr.insa.ms.orchestratorMS.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.orchestratorMS.model.Availability;
import fr.insa.ms.orchestratorMS.model.ChooseTutorRequest;
import fr.insa.ms.orchestratorMS.model.CreateDemandeResponse;
import fr.insa.ms.orchestratorMS.model.Request;
import fr.insa.ms.orchestratorMS.model.LeaveReviewRequest;
import fr.insa.ms.orchestratorMS.model.Skill;
import fr.insa.ms.orchestratorMS.model.Student;
import fr.insa.ms.orchestratorMS.model.UpdateDemandeStatusRequest;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorController {
	private final RestTemplate restTemplate;

	private static final String REQUEST_MS_BASE_URL = "http://requestMS/requests";
	private static final String RECOMMENDATION_MS_BASE_URL = "http://recommendationMS/recommendations";
	private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";
	private static final String ORCHESTRATOR_MS_BASE_URL = "http://orchestratorMS/orchestrator";

	public OrchestratorController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	// creates a demande and returns a list of recommended tutors
	@PostMapping("/createRequest")
	public ResponseEntity<?> createRequest(@RequestBody Request newDemande) {

		// POST call to create request
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Request> demandeRequest = new HttpEntity<>(newDemande, headers);

		try {
			ResponseEntity<Request> responseDemandePost = restTemplate.exchange(REQUEST_MS_BASE_URL, HttpMethod.POST,
					demandeRequest, Request.class);

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

		UpdateDemandeStatusRequest updateRequest = new UpdateDemandeStatusRequest(chooseTutorRequest.idDemande(),
				"En Cours", chooseTutorRequest.id_etudiant_demandeur(), chooseTutorRequest.idTuteur());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<UpdateDemandeStatusRequest> updateRequestEntity = new HttpEntity<>(updateRequest, headers);

		try {
			ResponseEntity<Map> response = restTemplate.exchange(ORCHESTRATOR_MS_BASE_URL + "/updateDemandeStatus",
					HttpMethod.PUT, updateRequestEntity, Map.class);

			return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

		} catch (

		HttpStatusCodeException ex) {
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}

	}

	@PutMapping("/updateDemandeStatus")
	public ResponseEntity<?> updateDemandeStatus(@RequestBody UpdateDemandeStatusRequest request) {

		try {
			ResponseEntity<Request> responseDemandeGet = restTemplate
					.exchange(REQUEST_MS_BASE_URL + "/" + request.idDemande(), HttpMethod.GET, null, Request.class);

			if (responseDemandeGet.getStatusCode().is2xxSuccessful()) {
				Request demande = responseDemandeGet.getBody();

				if (demande.id_etudiant_demandeur != request.id_etudiant_demandeur()) {
					return ResponseEntity.badRequest()
							.body("A student can only update status of a help request he made.");
				}
				if ("Attente".equals(request.statut()) && demande.id_etudiant_tuteur != 0) {
					return ResponseEntity.badRequest()
							.body("A help request status cannot be \"Attente\" if it already has a tutor assigned.");
				}
				if ("Réalisée".equals(request.statut()) && demande.id_etudiant_tuteur == 0) {
					return ResponseEntity.badRequest()
							.body("A help request status cannot be \"Réalisée\" if it doesn't have a tutor assigned.");
				}

				if ("En Cours".equals(request.statut())) {
					if (request.idTuteur() == null) {
						return ResponseEntity.badRequest()
								.body("A help request status cannot be \"En Cours\" if we are not assigning a tutor.");
					}

					if (demande.id_etudiant_tuteur != 0) {
						return ResponseEntity.badRequest().body(
								"A help request status cannot be \"En Cours\" if it already has a tutor assigned.");
					}
					if (request.idTuteur() == request.id_etudiant_demandeur()) {
						return ResponseEntity.badRequest().body("A student asking for help cannot be his own tutor.");
					}
					if (!"Attente".equals(demande.statut)) {
						return ResponseEntity.badRequest()
								.body("A tutor can only be assigned to a help request that has status \"Attente\".");
					}
					demande.id_etudiant_tuteur = request.idTuteur();
				}

				demande.statut = request.statut();

				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<Request> demandeEntity = new HttpEntity<>(demande, headers);

				ResponseEntity<Map> response = restTemplate.exchange(REQUEST_MS_BASE_URL, HttpMethod.PUT, demandeEntity,
						Map.class);

				return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
			}

			return ResponseEntity.status(responseDemandeGet.getStatusCode()).headers(responseDemandeGet.getHeaders())
					.body(responseDemandeGet.getBody());
		} catch (

		HttpStatusCodeException ex) {
			return ResponseEntity.status(ex.getStatusCode()).headers(ex.getResponseHeaders())
					.body(ex.getResponseBodyAsString());
		}

	}
	
	@PutMapping("/students/{studentId}")
	 public Student updateStudentInfos(
	         @PathVariable Integer studentId,
	         @RequestBody Student updatedStudent
	 ) {
	     String url = STUDENT_MS_BASE_URL + "/" + studentId;

	     Student student = restTemplate.getForObject(url, Student.class);
	     if (student == null) {
	         throw new RuntimeException("Student not found " + studentId);
	     }

	     student.setNom(updatedStudent.getNom());
	     student.setPrenom(updatedStudent.getPrenom());
	     student.setEmail(updatedStudent.getEmail());
	     student.setFiliere(updatedStudent.getFiliere());
	     student.setEtablissement(updatedStudent.getEtablissement());
	     student.setEstTuteur(updatedStudent.getEstTuteur());

	     restTemplate.put(url, student);
	     
	     return restTemplate.getForObject(url, Student.class);
	 }
	 
	 @PostMapping("/students/{studentId}/skills")
	 public List<Skill> addStudentSkills(
	         @PathVariable Integer studentId,
	         @RequestBody List<Skill> competences
	 ) {
		 String url = STUDENT_MS_BASE_URL + "/" + studentId + "/skills";

	     Skill[] addedSkills = restTemplate.postForObject(url, competences, Skill[].class);
	     return Arrays.asList(addedSkills);
	 }
	 
	 @PutMapping("/students/{studentId}/skills")
	 public List<Skill> updateStudentSkills(
	         @PathVariable Integer studentId,
	         @RequestBody List<Skill> competences
	 ) {
		 String url = STUDENT_MS_BASE_URL + "/" + studentId + "/skills";

	     restTemplate.put(url, competences);
	     
	     Skill[] updatedSkills = restTemplate.getForObject(url, Skill[].class);
	     return Arrays.asList(updatedSkills);
	 }
	 
	 @PostMapping("/students/{studentId}/availabilities")
	 public List<Availability> addStudentAvailabilities(
	         @PathVariable Integer studentId,
	         @RequestBody List<Availability> disponibilites
	 ) {
		 String url = STUDENT_MS_BASE_URL + "/" + studentId + "/availabilities";

		 Availability[] addedAvailabilities = restTemplate.postForObject(url, disponibilites, Availability[].class);
	     return Arrays.asList(addedAvailabilities);

	 }
	 
	 @PutMapping("/students/{studentId}/availabilities")
	 public List<Availability> updateStudentAvailabilities(
	         @PathVariable Integer studentId,
	         @RequestBody List<Availability> disponibilites
	 ) {
		 String url = STUDENT_MS_BASE_URL + "/" + studentId + "/availabilities";

	     restTemplate.put(url, disponibilites);
	     
	     Availability[] updatedAvailabilities = restTemplate.getForObject(url, Availability[].class);
	     return Arrays.asList(updatedAvailabilities);

	 }
	 
	    @PostMapping("/review")
	    public ResponseEntity<?> leaveReview(@RequestBody LeaveReviewRequest reviewRequest) {

	        try {
	        	//on recupere la demande initiale
	            ResponseEntity<Request> responseDemandeGet = restTemplate.exchange(
	                    REQUEST_MS_BASE_URL + "/" + reviewRequest.idDemande(),
	                    HttpMethod.GET,
	                    null,
	                    Request.class
	            );

	            if (!responseDemandeGet.getStatusCode().is2xxSuccessful()) {
	                return ResponseEntity
	                        .status(responseDemandeGet.getStatusCode())
	                        .headers(responseDemandeGet.getHeaders())
	                        .body(responseDemandeGet.getBody());
	            }

	            Request demande = responseDemandeGet.getBody();
	            if (demande == null) {
	                return ResponseEntity.badRequest().body("Demande introuvable.");
	            }

	            // on verifie que la demande nous appartient
	            if (!demande.id_etudiant_demandeur.equals(reviewRequest.id_etudiant_demandeur())) {
	                return ResponseEntity.badRequest()
	                        .body("L'étudiant ne peut laisser un avis que sur une demande qu'il a créée.");
	            }

	            // on verfie qu'un tuteur a bien ete assigne
	            if (demande.id_etudiant_tuteur == 0) {
	                return ResponseEntity.badRequest()
	                        .body("Aucun tuteur n'est associÃ© Ã  cette demande. Impossible de laisser un avis.");
	            }

	            // on verifie que la demande a ete realisee
	            if (!"Réalisée".equalsIgnoreCase(demande.statut)) {
	                return ResponseEntity.badRequest()
	                        .body("Un avis ne peut Ãªtre laissÃ© que pour une demande terminÃ©e.");
	            }

	            // on appelle studentMS pour creer l'avis
	            HttpHeaders headers = new HttpHeaders();
	            headers.setContentType(MediaType.APPLICATION_JSON);

	            Map<String, Object> reviewPayload = Map.of(
	                    "avis", reviewRequest.avis(),
	                    "idDemande", reviewRequest.idDemande(),
	                    "id_etudiant_demandeur", reviewRequest.id_etudiant_demandeur(),
	                    "idTuteur", demande.id_etudiant_tuteur   
	            );

	            HttpEntity<Map<String, Object>> reviewEntity = new HttpEntity<>(reviewPayload, headers);

	            ResponseEntity<Map> responseReviewPost = restTemplate.exchange(
	                    STUDENT_MS_BASE_URL + "/" + demande.id_etudiant_tuteur + "/review", 
	                    HttpMethod.POST,
	                    reviewEntity,
	                    Map.class
	            );

	            return ResponseEntity.status(responseReviewPost.getStatusCode())
	                    .body(responseReviewPost.getBody());

	        } catch (HttpStatusCodeException ex) {
	            return ResponseEntity.status(ex.getStatusCode())
	                    .headers(ex.getResponseHeaders())
	                    .body(ex.getResponseBodyAsString());
	        }
	    }

}