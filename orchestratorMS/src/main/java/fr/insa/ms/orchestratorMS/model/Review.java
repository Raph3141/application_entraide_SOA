package fr.insa.ms.orchestratorMS.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Review {

    private Integer idAvis;
	
    private String avis;
	
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
