package fr.insa.ms.studentMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.insa.ms.studentMS.model.Student;

//JpaRepository<Student,Integer> generic interface that provides all basic CRUD methods (Student = entity type, Integer = PK type)
// so by extending it we get access to many methods like findAll()...

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>{

}
