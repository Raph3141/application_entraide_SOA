package fr.insa.ms.studentMS.repository;

import fr.insa.ms.studentMS.model.Availability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
	List<Availability> findByStudent_id(Integer id);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Availability a WHERE a.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Integer studentId);
}
					