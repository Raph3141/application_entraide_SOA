package fr.insa.ms.recommendationMS.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.recommendationMS.model.Day;
import fr.insa.ms.recommendationMS.model.Demande;
import fr.insa.ms.recommendationMS.model.Student;

@RestController
@RequestMapping("/recommendations")
public class RecommendationRessource {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<?> getRecommendation(@RequestBody Demande demande) {

		Student[] tutorArray = restTemplate.getForObject("http://studentMS/students/helpers", Student[].class);

		List<Student> tutorList = Arrays.asList(tutorArray);
		
	    // Normalize keyword
	    String keyword = demande.mots_cles.toLowerCase().trim();

	    Day requestedDay = demande.date_souhaitee; 
	    
	    // Filter tutors
	    List<Student> recommendedTutors = tutorList.stream()

	        // competences
	        .filter(tutor -> tutor.getCompetences() != null)
	        .filter(tutor -> tutor.getCompetences().stream()
	                .anyMatch(skill ->
	                        skill.getCompetence() != null &&
	                        skill.getCompetence().toLowerCase().contains(keyword)
	                )
	        )

	        // availability
	        .filter(tutor -> tutor.getDisponibilites() != null)
	        .filter(tutor -> tutor.getDisponibilites().stream()
	                .anyMatch(dispo ->
	                        dispo.getJour() == requestedDay
	                )
	        )

	        .collect(Collectors.toList());

	    return ResponseEntity.ok(recommendedTutors);
	}

}
