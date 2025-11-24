package fr.insa.ms.orchestratorMS.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.orchestratorMS.model.Student;

@RestController
@RequestMapping("/orchestrator")
public class OrchestratorControllerRaph {
	 private final RestTemplate restTemplate;
	 
	 private static final String REQUEST_MS_BASE_URL = "http://requestMS/requests";
	 private static final String RECOMMENDATION_MS_BASE_URL = "http://recommendationMS/recommendation";
	 private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";
	 
	 public OrchestratorControllerRaph(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	 
	 @PutMapping("/students/{studentId}")
	 public Student updateStudentInfos(
	         @PathVariable Integer studentId,
	         @RequestParam(required = false) String nom,
	         @RequestParam(required = false) String prenom,
	         @RequestParam(required = false) String email,
	         @RequestParam(required = false) String filiere,
	         @RequestParam(required = false) String etablissement,
	         @RequestParam(required = false) Boolean estTuteur
	 ) {
	     String studentUrl = STUDENT_MS_BASE_URL + "/" + studentId;

	     Student student = restTemplate.getForObject(studentUrl, Student.class);
	     if (student == null) {
	         throw new RuntimeException("Student not found " + studentId);
	     }

	     if (nom != null) student.setNom(nom);
	     if (prenom != null) student.setPrenom(prenom);
	     if (email != null) student.setEmail(email);
	     if (filiere != null) student.setFiliere(filiere);
	     if (etablissement != null) student.setEtablissement(etablissement);
	     if (estTuteur != null) student.setEstTuteur(estTuteur);

	     restTemplate.put(studentUrl, student);

	     Student updatedStudent = restTemplate.getForObject(studentUrl, Student.class);

	     return updatedStudent;
	 }

}
