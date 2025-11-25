package fr.insa.ms.requestMS.model;

public class Demande {
	public int idDemande;
    public Integer id_etudiant_demandeur;
    public Integer id_etudiant_tuteur;
    public String titre;
    public String description;
    public String mots_cles;
    public Day date_souhaitee;
    public String statut;
}
