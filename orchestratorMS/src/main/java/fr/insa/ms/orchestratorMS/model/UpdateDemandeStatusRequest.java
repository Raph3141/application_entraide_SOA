package fr.insa.ms.orchestratorMS.model;

public record UpdateDemandeStatusRequest (int idDemande, String status, int idHelpSeeker, Integer idTutor) {

}
