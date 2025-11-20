package fr.insa.ms.recommendationMS.controller;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.recommendationMS.model.Demande;
import fr.insa.ms.recommendationMS.model.Student;

@RestController
@RequestMapping("/recommendation")
public class RecommendationRessource {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping
	public ResponseEntity<?> getRecommendation(@RequestBody Demande demande) {

		Student[] studentArray = restTemplate.getForObject("http://studentMS/students", Student[].class);

		List<Student> studentList = Arrays.asList(studentArray);
		System.out.println("PRINTING THE STUDENT LIST" + studentList.toString());
//		try {
//			int id = repo.createDemande(newDemande);
//
//			if (id == -1) {
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//						.body(Map.of("error", "Failed to insert demande"));
//			}
//
//			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idDemande", id));
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
//		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "current message temp"));

	}

}
