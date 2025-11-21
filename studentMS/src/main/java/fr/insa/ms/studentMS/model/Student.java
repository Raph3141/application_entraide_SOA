package fr.insa.ms.studentMS.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

//TEST 

@Entity //defines that the class can be mapped to a table in the DB
@Table(name="Etudiant") //The table’s name is Etudiant
public class Student {
	@Id //specifies the primary key of the entity
	@GeneratedValue(strategy = GenerationType.IDENTITY) //specify the primary key generation strategy to use
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
	
	//1 student -> many skills
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> competences = new ArrayList<>();
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Availability> disponibilites = new ArrayList<>();
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> avis = new ArrayList<>();
	
	
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
	public boolean isTuteur() {
		return estTuteur;
	}
	public void setEstTuteur(boolean estTuteur) {
		this.estTuteur = estTuteur;
	}

    public List<Availability> getDisponibilites() {
        return disponibilites;
    }

    public void setDisponibilites(List<Availability> disponibilites) {
        this.disponibilites = disponibilites;
    }

    public void addDisponibilites(Availability disponibilite) {
    	disponibilites.add(disponibilite);
        disponibilite.setStudent(this);
    }

    public void removeDisponibilite(Availability disponibilite) {
    	disponibilites.remove(disponibilite);
        disponibilite.setStudent(null);
    }
    
    public List<Skill> getCompetences() {
        return competences;
    }

    public void setCompetences(List<Skill> competences) {
        this.competences = competences;
    }

    public void addCompetence(Skill competence) {
        competences.add(competence);
        competence.setStudent(this);
    }

    public void removeCompetence(Skill competence) {
        competences.remove(competence);
        competence.setStudent(null);
    }
    
    public List<Review> getAvis() {
        return avis;
    }

    public void setAvis(List<Review> avis) {
        this.avis = avis;
    }

    public void addAvis(Review a) {
        avis.add(a);
        a.setStudent(this);
    }

    public void removeAvis(Review a) {
    	avis.remove(a);
        a.setStudent(null);
    }

}
