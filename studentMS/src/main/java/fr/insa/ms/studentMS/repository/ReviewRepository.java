package fr.insa.ms.studentMS.repository;

import fr.insa.ms.studentMS.model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByStudent_id(Integer id);
}
		