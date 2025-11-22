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

import fr.insa.ms.studentMS.model.Availability;
import fr.insa.ms.studentMS.model.Review;
import fr.insa.ms.studentMS.model.Skill;
import fr.insa.ms.studentMS.model.Student;
import fr.insa.ms.studentMS.repository.AvailabilityRepository;
import fr.insa.ms.studentMS.repository.ReviewRepository;
import fr.insa.ms.studentMS.repository.SkillRepository;
import fr.insa.ms.studentMS.repository.StudentRepository;

@RestController
@RequestMapping("/students")
public class StudentResource {
	
	private StudentRepository studentRepository;
	private SkillRepository skillRepository;
	private AvailabilityRepository availabilityRepository;
	private ReviewRepository reviewRepository;


	
	@Autowired
	public StudentResource(StudentRepository studentRepository, SkillRepository skillRepository, AvailabilityRepository availabilityRepository, ReviewRepository reviewRepository) {
		this.studentRepository = studentRepository;
		this.skillRepository = skillRepository;
		this.availabilityRepository = availabilityRepository;
		this.reviewRepository = reviewRepository;
	}
	
	@GetMapping
	public List<Student> getAllStudents(){
		return studentRepository.findAll();
	}
	
	@GetMapping("/helpers")
	public List<Student> getAllHelperStudents(){
		return studentRepository.findByestTuteurTrue();
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
    
    @GetMapping("/{id}/availabilities")
    public List<Availability> getAvailabilitiesOfStudent(@PathVariable Integer id) {
        return availabilityRepository.findByStudent_id(id);
    }
    
    @PostMapping("/{id}/availabilities")
    public List<Availability> addAvailabilitiesToStudent(@PathVariable Integer id,  @RequestBody List<Availability> disponibilites) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        for (Availability availability : disponibilites) {
        	availability.setStudent(student);
            student.getDisponibilites().add(availability);
        }

        return availabilityRepository.saveAll(disponibilites);
    }
    
    @PutMapping("/{id}/availabilities")
    public List<Availability> replaceAvailabilitiesofStudent(@PathVariable Integer id,  @RequestBody List<Availability> newAvailabilities) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Availability> oldAvailabilities = availabilityRepository.findByStudent_id(id);
        availabilityRepository.deleteAll(oldAvailabilities);

        for (Availability availability : newAvailabilities) {
        	availability.setStudent(student);
        }

        return availabilityRepository.saveAll(newAvailabilities);
	}
    
    @DeleteMapping("/{id}/availabilities")
    public void deleteAllAvailabilitiesOfStudent(@PathVariable Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Availability> availabilities = availabilityRepository.findByStudent_id(id);

        for (Availability availability : availabilities) {
        	availability.setStudent(null);
        }
        student.getDisponibilites().clear();

        availabilityRepository.deleteAll(availabilities);
    }
    
    @DeleteMapping("/{id}/availabilities/{availabilityId}")
    public void deleteAvailabilityOfStudent(@PathVariable Integer id, @PathVariable Integer availabilityId) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        Availability availability = availabilityRepository.findById(availabilityId).orElseThrow(() -> new RuntimeException("Skill not found with id " + availabilityId));

        if (availability.getStudent() == null || !availability.getStudent().getId().equals(id)) {
            throw new RuntimeException("Skill " + availabilityId + " does not belong to student " + id);
        }

        student.getDisponibilites().remove(availability);

        availabilityRepository.delete(availability);
    }
    
    @GetMapping("/{id}/reviews")
    public List<Review> getReviewsOfStudent(@PathVariable Integer id) {
        return reviewRepository.findByStudent_id(id);
    }
    
    @PostMapping("/{id}/reviews")
    public List<Review> addReviewsToStudent(@PathVariable Integer id,  @RequestBody List<Review> reviews) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        for (Review review : reviews) {
        	review.setStudent(student);
            student.getAvis().add(review);
        }

        return reviewRepository.saveAll(reviews);
    }
    
    @PutMapping("/{id}/reviews")
    public List<Review> replaceReviewsofStudent(@PathVariable Integer id,  @RequestBody List<Review> newReviews) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Review> oldReviews = reviewRepository.findByStudent_id(id);
        reviewRepository.deleteAll(oldReviews);

        for (Review review : newReviews) {
        	review.setStudent(student);
        }

        return reviewRepository.saveAll(newReviews);
	}
    
    @DeleteMapping("/{id}/reviews")
    public void deleteAllReviewsOfStudent(@PathVariable Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id " + id));

        List<Review> reviews = reviewRepository.findByStudent_id(id);

        for (Review review : reviews) {
        	review.setStudent(null);
        }
        student.getAvis().clear();

        reviewRepository.deleteAll(reviews);
    }
    
    @GetMapping("/email/{email}")
    public Student getStudentByEmail(@PathVariable String email) {
    	return studentRepository.findByemail(email).orElseThrow(() -> new RuntimeException("Student not found with email " + email));
    }
 
}  
