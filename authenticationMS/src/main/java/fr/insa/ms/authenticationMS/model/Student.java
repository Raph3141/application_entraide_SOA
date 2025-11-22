package fr.insa.ms.authenticationMS.model;

public class Student {
	private Integer id;
	private String nom;
	private String prenom;
	private String email;
	private String filiere;
	private String etablissement;
	private boolean estTuteur;
	private String mdp;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public boolean isTuteur() {
		return estTuteur;
	}
	public void setEstTuteur(boolean estTuteur) {
		this.estTuteur = estTuteur;
	}

}
