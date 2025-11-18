package fr.insa.ms.studentMS.model;

import java.util.List;

public class Student {
	private int id;
	private String nom;
	private String prenom;
	private String email;
	private String filiere;
	private String etablissement;
	private boolean estTuteur;
	private String pwd;
	private List<String> competences;
	private List<String> disponibilites;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean isEstTuteur() {
		return estTuteur;
	}
	public void setEstTuteur(boolean estTuteur) {
		this.estTuteur = estTuteur;
	}
	public List<String> getDisponibilites() {
		return disponibilites;
	}
	public void setDisponibilites(List<String> disponibilites) {
		this.disponibilites = disponibilites;
	}
	public List<String> getCompetences() {
		return competences;
	}
	public void setCompetences(List<String> competences) {
		this.competences = competences;
	}

}
