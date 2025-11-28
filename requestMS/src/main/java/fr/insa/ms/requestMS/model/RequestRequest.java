package fr.insa.ms.requestMS.model;

public class RequestRequest {

    public Integer id_etudiant_demandeur;
    
    public Integer id_etudiant_tuteur;
	
    public String titre;
	
	public String description;
	
    public String mots_cles;
	
    public Day date_souhaitee;
	
    public String statut;

	public Integer getId_etudiant_demandeur() {
		return id_etudiant_demandeur;
	}

	public void setId_etudiant_demandeur(Integer id_etudiant_demandeur) {
		this.id_etudiant_demandeur = id_etudiant_demandeur;
	}

	public Integer getId_etudiant_tuteur() {
		return id_etudiant_tuteur;
	}

	public void setId_etudiant_tuteur(Integer id_etudiant_tuteur) {
		this.id_etudiant_tuteur = id_etudiant_tuteur;
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

   
}
