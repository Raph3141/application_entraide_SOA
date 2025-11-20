package fr.insa.ms.studentMS.repository;

import fr.insa.ms.studentMS.model.Availability;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Integer> {
	List<Availability> findByStudent_id(Integer id);
}
					