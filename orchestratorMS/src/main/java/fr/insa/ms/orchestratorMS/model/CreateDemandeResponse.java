package fr.insa.ms.orchestratorMS.model;

import java.util.List;


public record CreateDemandeResponse (Request demande, List<Student> tuteursRecommandés) {

	public CreateDemandeResponse(Request demande, List<Student> tuteursRecommandés) {
		this.tuteursRecommandés = tuteursRecommandés;
		this.demande = demande;
	}
	
}
