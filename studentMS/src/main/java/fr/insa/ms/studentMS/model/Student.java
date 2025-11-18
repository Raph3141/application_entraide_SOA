package fr.insa.ms.studentMS.model;

import jakarta.persistence.*;

@Entity //This tells JPA: this class is mapped to a table in the database
@Table(name="Etudiant") //The table’s name is Etudiant
public class Student {
	@Id //Primary key field
	@GeneratedValue(strategy = GenerationType.IDENTITY) //DB will auto-generate the ID
	@Column(name = "idEtudiant")
	private Integer id;
	@Column(name = "nom")
	private String nom;
	@Column(name = "prenom")
	private String prenom;
	@Column(name = "email")
	private String email;
	@Column(name = "filiere")
	private String filiere;
	@Column(name = "etablissement")
	private String etablissement;
	@Column(name = "estTuteur")
	private boolean estTuteur;
	@Column(name = "mdp")
	private String mdp;
	/*private List<String> competences;
	private List<String> disponibilites;*/
	
	public Student() {
		
	}
	
	public Student(Integer id, String nom, String prenom, String email, String filiere, String etablissement, boolean estTuteur, String mdp) {
		this.id=id;
		this.nom=nom;
		this.prenom=prenom;
		this.email=email;
		this.filiere=filiere;
		this.etablissement=etablissement;
		this.estTuteur=estTuteur;
		this.mdp=mdp;
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
	public String getMdp() {
		return mdp;
	}
	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	public boolean isEstTuteur() {
		return estTuteur;
	}
	public void setEstTuteur(boolean estTuteur) {
		this.estTuteur = estTuteur;
	}
	/*public List<String> getDisponibilites() {
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
	}*/

}
