package fr.insa.ms.orchestratorMS.model;

public record UpdateDemandeStatusRequest (Integer idDemande, String statut, Integer id_etudiant_demandeur, Integer idTuteur) {

}
