package fr.insa.ms.studentMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "Avis")
public class Review {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAvis")
    private Integer idAvis;
	
	@Column(name = "avis")
    private String avis;
	
	@ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "idEtudiant")
    @JsonIgnore
    private Student student;
	
	public Review() {
    }

    public Review(Integer idAvis, String avis, Student student) {
        this.idAvis = idAvis;
        this.avis = avis;
        this.student = student;
    }

    public Integer getIdAvis() {
        return idAvis;
    }

    public void setIdAvis(Integer idAvis) {
        this.idAvis = idAvis;
    }

    public String getAvis() {
        return avis;
    }

    public void setAvis(String avis) {
        this.avis = avis;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
