package fr.insa.ms.studentMS.repository;

import fr.insa.ms.studentMS.model.Skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
	List<Skill> findByStudent_Id(Integer id);
}
