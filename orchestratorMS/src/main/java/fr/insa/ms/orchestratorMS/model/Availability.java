package fr.insa.ms.orchestratorMS.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Availability {
	private Integer idDisponibilite;
	private Day jour;
	private LocalTime heure_deb;
	private LocalTime heure_fin;
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
