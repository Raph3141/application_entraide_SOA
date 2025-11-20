package fr.insa.ms.studentMS.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="Disponibilite")
public class Availability {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idDisponibilite")
	private Integer idDisponibilite;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "jour")
	private Day jour;
	
	@Column(name = "heure_deb")
	private LocalTime heure_deb;
	
	@Column(name = "heure_fin")
	private LocalTime heure_fin;
	
	@ManyToOne
    @JoinColumn(name = "id_etudiant", referencedColumnName = "idEtudiant")
    @JsonIgnore
    private Student student;
	
	public Availability() {
		
	}
	
	public Availability(Integer idDisponibilite, Day jour, LocalTime heure_deb, LocalTime heure_fin, Student student) {
		this.idDisponibilite = idDisponibilite;
		this.jour=jour;
		this.heure_deb=heure_deb;
		this.heure_fin=heure_fin;
		this.student=student;
	}
	
	 public Integer getIdDisponibilite() {
	        return idDisponibilite;
	    }

	    public void getIdDisponibilite(Integer idDisponibilite) {
	        this.idDisponibilite = idDisponibilite;
	    }

	    public Day getJour() {
	        return jour;
	    }

	    public void setJour(Day jour) {
	        this.jour = jour;
	    }

	    public LocalTime getHeureDeb() {
	        return heure_deb;
	    }

	    public void setHeureDeb(LocalTime heure_deb) {
	        this.heure_deb = heure_deb;
	    }

	    public LocalTime getHeureFin() {
	        return heure_fin;
	    }

	    public void setHeureFin(LocalTime heure_fin) {
	        this.heure_fin = heure_fin;
	    }

	    public Student getStudent() {
	        return student;
	    }

	    public void setStudent(Student student) {
	        this.student = student;
	    }
	
}
