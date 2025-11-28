package fr.insa.ms.requestMS.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name="Demande") 
public class Request {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idDemande")
	public Integer idDemande;
	
	@ManyToOne
    @JoinColumn(name = "id_etudiant_demandeur", referencedColumnName = "idEtudiant")
    public Student etudiantDemandeur;
    
	@ManyToOne
    @JoinColumn(name = "id_etudiant_tuteur", referencedColumnName = "idEtudiant")
    public Student etudiantTuteur;
	
	@Column(name = "titre")
    public String titre;
	
	@Column(name = "description")
	public String description;
	
	@Column(name = "mots_cles")
    public String mots_cles;
	
	@Column(name = "date_souhaitee")
    public Day date_souhaitee;
	
	@Column(name = "statut")
    public String statut;

	public Integer getIdDemande() {
		return idDemande;
	}

	public void setIdDemande(Integer idDemande) {
		this.idDemande = idDemande;
	}

	public Student getEtudiantDemandeur() {
		return etudiantDemandeur;
	}

	public void setEtudiantDemandeur(Student etudiantDemandeur) {
		this.etudiantDemandeur = etudiantDemandeur;
	}

	public Student getEtudiantTuteur() {
		return etudiantTuteur;
	}

	public void setEtudiantTuteur(Student etudiantTuteur) {
		this.etudiantTuteur = etudiantTuteur;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMots_cles() {
		return mots_cles;
	}

	public void setMots_cles(String mots_cles) {
		this.mots_cles = mots_cles;
	}

	public Day getDate_souhaitee() {
		return date_souhaitee;
	}

	public void setDate_souhaitee(Day date_souhaitee) {
		this.date_souhaitee = date_souhaitee;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

	public Request(Student etudiantDemandeur, Student etudiantTuteur, String titre, String description,
			String mots_cles, Day date_souhaitee, String statut) {
		super();
		this.etudiantDemandeur = etudiantDemandeur;
		this.etudiantTuteur = etudiantTuteur;
		this.titre = titre;
		this.description = description;
		this.mots_cles = mots_cles;
		this.date_souhaitee = date_souhaitee;
		this.statut = statut;
	}
	
	
}
