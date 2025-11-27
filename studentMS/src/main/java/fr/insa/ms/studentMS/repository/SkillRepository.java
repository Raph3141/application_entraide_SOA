package fr.insa.ms.studentMS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.insa.ms.studentMS.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
	List<Skill> findByStudent_id(Integer id);
	
	@Modifying
    @Transactional
    @Query("DELETE FROM Skill s WHERE s.student.id = :studentId")
    void deleteByStudentId(@Param("studentId") Integer studentId);
}
					