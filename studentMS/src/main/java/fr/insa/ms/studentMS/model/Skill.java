package fr.insa.ms.studentMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "Competence")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCompetence") //PK dans Competence
    private Integer idCompetence;

    @Column(name = "competence")
    private String competence;

    //many skills -> 1 student
    @ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "idEtudiant")
    @JsonIgnore
    private Student student;

    public Skill() {
    }

    public Skill(Integer idCompetence, String competence, Student student) {
        this.idCompetence = idCompetence;
        this.competence = competence;
        this.student = student;
    }

    public Integer getIdCompetence() {
        return idCompetence;
    }

    public void setIdCompetence(Integer idCompetence) {
        this.idCompetence = idCompetence;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }
}
