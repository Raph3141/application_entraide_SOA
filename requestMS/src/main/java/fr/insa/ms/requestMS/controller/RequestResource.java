package fr.insa.ms.requestMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.requestMS.model.Request;
import fr.insa.ms.requestMS.model.RequestRequest;
import fr.insa.ms.requestMS.model.Student;
import fr.insa.ms.requestMS.repo.RequestRepository;

@RestController
@RequestMapping("/requests")
public class RequestResource {

	private final RestTemplate restTemplate;

	private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";

	private RequestRepository requestRepository;

	@Autowired
	public RequestResource(RequestRepository requestRepository, RestTemplate restTemplate) {
		this.requestRepository = requestRepository;
		this.restTemplate = restTemplate;
	}
	
	@PostMapping
	public ResponseEntity<?> createRequest(@RequestBody RequestRequest newRequest) {
		
		ResponseEntity<Student> responseGetStudentById = restTemplate
				.exchange(STUDENT_MS_BASE_URL + "/" + newRequest.getId_etudiant_demandeur(), HttpMethod.GET, null, Student.class);
		
		Student studentHelpSeeker = responseGetStudentById.getBody();
		
		Request requestToSave = new Request(studentHelpSeeker, null, newRequest.titre, newRequest.description, newRequest.mots_cles, newRequest.date_souhaitee, newRequest.statut);
		
		try {
	        Request saved = requestRepository.save(requestToSave);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    }
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updateRequest(@PathVariable Integer id,
	                                       @RequestBody Request updatedRequest) {
	    return requestRepository.findById(id)
	            .map(request -> {
	            	request.setTitre(updatedRequest.titre);
	            	request.setEtudiantTuteur(updatedRequest.etudiantTuteur);
	            	request.setDescription(updatedRequest.description);
	            	request.setMots_cles(updatedRequest.mots_cles);
	            	request.setDate_souhaitee(updatedRequest.date_souhaitee);
	            	request.setStatut(updatedRequest.statut);

	            	Request saved = requestRepository.save(request);
	                return ResponseEntity.ok(saved);
	            })
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRequest(@PathVariable Integer id) {
	    if (!requestRepository.existsById(id)) {
	    	return ResponseEntity.notFound().build();
	    	}
	    requestRepository.deleteById(id);
	    return ResponseEntity.ok().build(); 
	}

	@GetMapping("/{id}")
	public ResponseEntity<Request> getRequestById(@PathVariable Integer id) {
	    return requestRepository.findById(id)
	            .map(ResponseEntity::ok)                
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@GetMapping
	public ResponseEntity<List<Request>> getAllRequests() {
	    return ResponseEntity.ok(requestRepository.findAll());
	}
	
	
}
