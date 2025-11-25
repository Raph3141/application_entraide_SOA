package fr.insa.ms.orchestratorMS.model;

import java.util.List;


public record CreateDemandeResponse (Demande demande, List<Student> recommendedTutors) {

	public CreateDemandeResponse(Demande demande, List<Student> recommendedTutors) {
		this.recommendedTutors = recommendedTutors;
		this.demande = demande;
	}
	
}
