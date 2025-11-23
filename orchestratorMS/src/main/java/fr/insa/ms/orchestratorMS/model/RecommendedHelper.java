package fr.insa.ms.orchestratorMS.model;

import java.util.List;

public class RecommendedHelper {
	private Integer studentId;
	private String nom;
	private String prenom;
	private String filiere;
	private String etablissement;
	private List<String> competences;
	private List<String> avis;
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
	public String getEtablissement() {
		return etablissement;
	}
	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}
	public List<String> getCompetences() {
		return competences;
	}
	public void setCompetences(List<String> competences) {
		this.competences = competences;
	}
	public List<String> getAvis() {
		return avis;
	}
	public void setAvis(List<String> avis) {
		this.avis = avis;
	}
}
