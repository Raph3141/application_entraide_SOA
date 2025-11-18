package fr.insa.ms.studentMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.studentMS.model.Student;
import fr.insa.ms.studentMS.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentResource {
	
	private StudentRepository studentRepository;
	
	@Autowired
	public StudentResource(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}
	
	@GetMapping
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
}
