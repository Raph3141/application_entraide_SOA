package fr.insa.ms.requestMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.insa.ms.requestMS.model.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request,Integer>{
}
