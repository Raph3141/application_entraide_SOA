package fr.insa.ms.recommendationMS.model;

public class Skill {
	
    private Integer idCompetence;

    private String competence;

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
