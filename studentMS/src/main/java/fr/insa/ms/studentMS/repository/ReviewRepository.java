package fr.insa.ms.studentMS.repository;

import fr.insa.ms.studentMS.model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByStudent_id(Integer id);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Review r WHERE r.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Integer studentId);
}
		