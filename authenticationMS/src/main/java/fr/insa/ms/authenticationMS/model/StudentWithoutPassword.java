package fr.insa.ms.authenticationMS.model;

public class StudentWithoutPassword {
	private Integer id;
	private String nom;
	private String prenom;
	private String email;
	private String filiere;
	private String etablissement;
	private Boolean estTuteur;

	
	public StudentWithoutPassword(Student student) {
		super();
		this.id = student.getId();
		this.nom = student.getNom();
		this.prenom = student.getPrenom();
		this.email = student.getEmail();
		this.filiere = student.getFiliere();
		this.etablissement = student.getEtablissement();
		this.estTuteur = student.getEstTuteur();
	}
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
	public Boolean getEstTuteur() {
		return estTuteur;
	}
	public void setEstTuteur(Boolean estTuteur) {
		this.estTuteur = estTuteur;
	}

}
