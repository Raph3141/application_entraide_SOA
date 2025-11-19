package fr.insa.ms.studentMS.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.studentMS.model.Skill;
import fr.insa.ms.studentMS.model.Student;
import fr.insa.ms.studentMS.repository.SkillRepository;
import fr.insa.ms.studentMS.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentResource {
	
	private StudentRepository studentRepository;
	private SkillRepository skillRepository;

	
	@Autowired
	public StudentResource(StudentRepository studentRepository, SkillRepository skillRepository) {
		this.studentRepository = studentRepository;
		this.skillRepository = skillRepository;
	}
	
	@GetMapping
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Student> getStudentById(@PathVariable Integer id){
		if (!studentRepository.existsById(id)) {
	        throw new RuntimeException("Student not found with id " + id);
	    }
	    return studentRepository.findById(id);
	}
	
	@PostMapping
	public Student createStudent(@RequestBody Student student) {
	    return studentRepository.save(student);
	}
	
	@DeleteMapping("/{id}")
	public void deleteStudent(@PathVariable Integer id) {
	    if (!studentRepository.existsById(id)) {
	        throw new RuntimeException("Student not found with id " + id);
	    }
	    studentRepository.deleteById(id);
	}

	@PutMapping("/{id}")
	public Student updateStudent(@PathVariable Integer id, @RequestBody Student updatedStudent) {
	    Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

	    student.setNom(updatedStudent.getNom());
	    student.setPrenom(updatedStudent.getPrenom());
	    student.setEmail(updatedStudent.getEmail());
	    student.setFiliere(updatedStudent.getFiliere());
	    student.setEtablissement(updatedStudent.getEtablissement());
	    student.setEstTuteur(updatedStudent.isTuteur());
	    student.setMdp(updatedStudent.getMdp());

	    return studentRepository.save(student);
	}
	
	@PostMapping("/{id}/skills")
    public List<Skill> addSkillsToStudent(@PathVariable Integer id,  @RequestBody List<Skill> competences) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        for (Skill skill : competences) {
            skill.setStudent(student);
            student.getCompetences().add(skill);
        }

        return skillRepository.saveAll(competences);
    }
	
	@PutMapping("/{id}/skills")
    public List<Skill> replaceSkillsofStudent(@PathVariable Integer id,  @RequestBody List<Skill> newSkills) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Skill> oldSkills = skillRepository.findByStudent_id(id);
        skillRepository.deleteAll(oldSkills);

        for (Skill skill : newSkills) {
            skill.setStudent(student);
        }

        return skillRepository.saveAll(newSkills);
	}

    @GetMapping("/{id}/skills")
    public List<Skill> getSkillsOfStudent(@PathVariable Integer id) {
        return skillRepository.findByStudent_id(id);
    }
    
    @DeleteMapping("/{id}/skills")
    public void deleteAllSkillsOfStudent(@PathVariable Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Skill> skills = skillRepository.findByStudent_id(id);

        for (Skill skill : skills) {
        	skill.setStudent(null);
        }
        student.getCompetences().clear();

        skillRepository.deleteAll(skills);
    }

    @DeleteMapping("/{id}/skills/{skillId}")
    public void deleteSkillOfStudent(@PathVariable Integer id, @PathVariable Integer skillId) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        Skill skill = skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found with id " + skillId));

        if (skill.getStudent() == null || !skill.getStudent().getId().equals(id)) {
            throw new RuntimeException("Skill " + skillId + " does not belong to student " + id);
        }

        student.getCompetences().remove(skill);

        skillRepository.delete(skill);
    }


}  
