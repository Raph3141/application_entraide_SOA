package fr.insa.ms.orchestratorMS.model;

public class HelpRequest {

    private int idDemande;
    private int id_etudiant_demandeur;
    private String titre;
    private String description;
    private String mots_cles;
    private String date_souhaitee;
    private String statut;
    private int id_etudiant_aideur;

    public int getIdDemande() {
        return idDemande;
    }

    public void setIdDemande(int idDemande) {
        this.idDemande = idDemande;
    }

    public int getId_etudiant_demandeur() {
        return id_etudiant_demandeur;
    }

    public void setId_etudiant_demandeur(int id_etudiant_demandeur) {
        this.id_etudiant_demandeur = id_etudiant_demandeur;
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

    public String getDate_souhaitee() {
        return date_souhaitee;
    }

    public void setDate_souhaitee(String date_souhaitee) {
        this.date_souhaitee = date_souhaitee;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getId_etudiant_aideur() {
        return id_etudiant_aideur;
    }

    public void setId_etudiant_aideur(int id_etudiant_aideur) {
        this.id_etudiant_aideur = id_etudiant_aideur;
    }
}
