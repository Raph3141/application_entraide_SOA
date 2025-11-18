package fr.insa.ms.studentMS.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentResource {
	@GetMapping("/students")
	
	public int studentNumber() {
		return 200;
	}

}
