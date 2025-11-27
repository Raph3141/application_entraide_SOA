package fr.insa.ms.authenticationMS.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.authenticationMS.model.AuthResponse;
import fr.insa.ms.authenticationMS.model.LoginRequest;
import fr.insa.ms.authenticationMS.model.RegisterRequest;
import fr.insa.ms.authenticationMS.model.Student;
import fr.insa.ms.authenticationMS.model.StudentWithoutPassword;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final RestTemplate restTemplate;
	private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";

	public AuthController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		String url = STUDENT_MS_BASE_URL + "/email/" + request.getEmail();
		Student student;
		try {
			student = restTemplate.getForObject(url, Student.class);
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body(new AuthResponse("Error calling studentMS: " + e.getMessage(), null));
		}

		if (student == null || !student.getMdp().equals(request.getMdp())) {
			return ResponseEntity.status(401).body(new AuthResponse("Invalid email or password", null));
		}

		StudentWithoutPassword response = new StudentWithoutPassword(student);
		return ResponseEntity.ok(new AuthResponse("Login successful", response));
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		Student newStudent = new Student();
		newStudent.setNom(request.getNom());
		newStudent.setPrenom(request.getPrenom());
		newStudent.setEmail(request.getEmail());
		newStudent.setFiliere(request.getFiliere());
		newStudent.setEtablissement(request.getEtablissement());
		newStudent.setEstTuteur(request.getEstTuteur());
		newStudent.setMdp(request.getMdp());

		Student createdStudent;
		try {
			createdStudent = restTemplate.postForObject(STUDENT_MS_BASE_URL, newStudent, Student.class);
		} catch (Exception e) {
			return ResponseEntity.status(500)
					.body(new AuthResponse("Error calling studentMS: " + e.getMessage(), null));
		}
		if (createdStudent == null) {
			return ResponseEntity.status(500).body(new AuthResponse("Failed to create student", null));
		}

		StudentWithoutPassword response = new StudentWithoutPassword(createdStudent);
		return ResponseEntity.status(201).body(new AuthResponse("Registration successful", response));
	}

}
