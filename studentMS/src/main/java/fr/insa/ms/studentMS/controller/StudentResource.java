package fr.insa.ms.studentMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<Student>> getAllStudents() {
	    return ResponseEntity.ok(studentRepository.findAll());
	}
	
	@GetMapping("/helpers")
	public ResponseEntity<List<Student>> getAllHelperStudents() {
	    return ResponseEntity.ok(studentRepository.findByestTuteurTrue());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable Integer id) {
	    return studentRepository.findById(id)
	            .map(ResponseEntity::ok)                
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public ResponseEntity<?> createStudent(@RequestBody Student student) {
	    try {
	    	Student saved = studentRepository.save(student);
	        return ResponseEntity.status(HttpStatus.CREATED).body(saved);

	    } catch (Exception e) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(e.getMessage());
	    }
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {
	    if (!studentRepository.existsById(id)) {
	    	return ResponseEntity.notFound().build();
	    	}
	    studentRepository.deleteById(id);
	    return ResponseEntity.ok().build(); 
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateStudent(@PathVariable Integer id,
	                                       @RequestBody Student updatedStudent) {
	    return studentRepository.findById(id)
	            .map(student -> {
	                student.setNom(updatedStudent.getNom());
	                student.setPrenom(updatedStudent.getPrenom());
	                student.setEmail(updatedStudent.getEmail());
	                student.setFiliere(updatedStudent.getFiliere());
	                student.setEtablissement(updatedStudent.getEtablissement());
	                student.setEstTuteur(updatedStudent.getEstTuteur());
	                student.setMdp(updatedStudent.getMdp());

	                Student saved = studentRepository.save(student);
	                return ResponseEntity.ok(saved);
	            })
	            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

	@PostMapping("/{id}/skills")
	public ResponseEntity<?> addSkillsToStudent(
	        @PathVariable Integer id,
	        @RequestBody List<Skill> competences
	) {
	    return studentRepository.findById(id)
	            .map(student -> {
	                for (Skill skill : competences) {
	                    skill.setStudent(student);
	                    student.getCompetences().add(skill);
	                }
	                return ResponseEntity.status(HttpStatus.CREATED)
	                        .body(skillRepository.saveAll(competences));
	            })
	            .orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@PutMapping("/{id}/skills")
    public ResponseEntity<List<Skill>> replaceSkillsofStudent(@PathVariable Integer id,  @RequestBody List<Skill> newSkills) {
        return studentRepository.findById(id)
                .map(student -> {

                	skillRepository.deleteByStudentId(id);

                    for (Skill skill : newSkills) {
                        skill.setStudent(student);
                    }

                    List<Skill> saved = skillRepository.saveAll(newSkills);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}

    @GetMapping("/{id}/skills")
    public ResponseEntity<List<Skill>> getSkillsOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	
	    return ResponseEntity.ok(skillRepository.findByStudent_id(id));
    }
    
    @DeleteMapping("/{id}/skills")
    public ResponseEntity<Void> deleteAllSkillsOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        skillRepository.deleteByStudentId(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/skills/{skillId}")
    public ResponseEntity<Void> deleteSkillOfStudent(@PathVariable Integer id, @PathVariable Integer skillId) {
    	if (!skillRepository.existsById(skillId)) {
    	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	availabilityRepository.deleteById(skillId);
    	return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/availabilities")
    public ResponseEntity<List<Availability>> getAvailabilitiesOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	
        return ResponseEntity.ok(availabilityRepository.findByStudent_id(id));
    }
    
    @PostMapping("/{id}/availabilities")
    public ResponseEntity<List<Availability>> addAvailabilitiesToStudent(@PathVariable Integer id,  @RequestBody List<Availability> disponibilites) {
    	 return studentRepository.findById(id)
 	            .map(student -> {
 	                for (Availability availability : disponibilites) {
 	                	availability.setStudent(student);
 	                }
 	                return ResponseEntity.status(HttpStatus.CREATED)
 	                        .body(availabilityRepository.saveAll(disponibilites));
 	            })
 	            .orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PutMapping("/{id}/availabilities")
    public ResponseEntity<List<Availability>> replaceAvailabilitiesofStudent(@PathVariable Integer id,  @RequestBody List<Availability> newAvailabilities) {
        return studentRepository.findById(id)
                .map(student -> {

                	availabilityRepository.deleteByStudentId(id);

                	for (Availability availability : newAvailabilities) {
                		availability.setStudent(student);
                    }

                    List<Availability> saved = availabilityRepository.saveAll(newAvailabilities);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
    
    @DeleteMapping("/{id}/availabilities")
    public ResponseEntity<Void> deleteAllAvailabilitiesOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	availabilityRepository.deleteByStudentId(id);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}/availabilities/{availabilityId}")
    public ResponseEntity<Void> deleteAvailabilityOfStudent(@PathVariable Integer id, @PathVariable Integer availabilityId) {
    	if (!availabilityRepository.existsById(availabilityId)) {
    	    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    	}
    	availabilityRepository.deleteById(availabilityId);
    	return ResponseEntity.ok().build();
    }
    
    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> getReviewsOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	
        return ResponseEntity.ok(reviewRepository.findByStudent_id(id));
    }
    
    @PostMapping("/{id}/review")
    public ResponseEntity<Review> addReviewToStudent(@PathVariable Integer id,  @RequestBody Review review) {
    	
    	return studentRepository.findById(id)
 	            .map(student -> {
 	                return ResponseEntity.status(HttpStatus.CREATED)
 	                        .body(reviewRepository.save(review));
 	            })
 	            .orElseGet(() ->  ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @PutMapping("/{id}/reviews")
    public ResponseEntity<List<Review>> replaceReviewsofStudent(@PathVariable Integer id,  @RequestBody List<Review> newReviews) {

        return studentRepository.findById(id)
                .map(student -> {

                	reviewRepository.deleteByStudentId(id);

                	for (Review review : newReviews) {
                    	review.setStudent(student);
                    }

                    List<Review> saved = reviewRepository.saveAll(newReviews);
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() ->
                    ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @DeleteMapping("/{id}/reviews")
    public ResponseEntity<Void> deleteAllReviewsOfStudent(@PathVariable Integer id) {
    	if (!studentRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    	reviewRepository.deleteByStudentId(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Student> getStudentByEmail(@PathVariable String email) {
	    return studentRepository.findByemail(email)
	            .map(ResponseEntity::ok)                
	            .orElseGet(() -> ResponseEntity.notFound().build());
	}
 
}  
