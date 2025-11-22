package fr.insa.ms.authenticationMS.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import fr.insa.ms.authenticationMS.model.AuthResponse;
import fr.insa.ms.authenticationMS.model.LoginRequest;
import fr.insa.ms.authenticationMS.model.RegisterRequest;
import fr.insa.ms.authenticationMS.model.Student;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private final RestTemplate restTemplate;
	//private static final String STUDENT_MS_BASE_URL = "http://localhost:8080/students";
	private static final String STUDENT_MS_BASE_URL = "http://studentMS/students";
	
	public AuthController(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request) {
		String url = STUDENT_MS_BASE_URL + "/email/" + request.getEmail();
		Student student;
		try {
			student = restTemplate.getForObject(url, Student.class);
		} catch (Exception e){
			throw new RuntimeException("Error calling studentMS : "+ e.getMessage());
		}
		
		if (student == null || student.getMdp() == null || !student.getMdp().equals(request.getMdp())) {
			throw new RuntimeException("Invalid email or password");
	}
		
		return new AuthResponse("Login Successful", student);
	
	}
	
	@PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {

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
            createdStudent = restTemplate.postForObject(
                    STUDENT_MS_BASE_URL, 
                    newStudent,
                    Student.class
            );
        } catch (Exception e) {
            throw new RuntimeException("Error calling studentMS : " + e.getMessage());
        }

        if (createdStudent == null) {
            throw new RuntimeException("Failed to create student");
        }

        return new AuthResponse("Registration Successful", createdStudent);
	}
	
}
