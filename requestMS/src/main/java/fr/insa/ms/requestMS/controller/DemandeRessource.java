package fr.insa.ms.requestMS.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.requestMS.model.Demande;
import fr.insa.ms.requestMS.repo.DemandeRepository;

@RestController
@RequestMapping("/requests")
public class DemandeRessource {

    private final DemandeRepository repo;

    public DemandeRessource(DemandeRepository repo) {
        this.repo = repo;
    }
    
    @PostMapping
    public ResponseEntity<?> createDemande(@RequestBody Demande newDemande) {
        try {
            int id = repo.createDemande(newDemande);

            if (id == -1) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to insert demande"));
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("idDemande", id));

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<?> updateDemande(@RequestBody Demande demande) {
        try {
        	boolean updated = repo.updateDemande(demande);

            if (!updated) {
                return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of("error", "Failed to update demande"));
            }

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of("updated", updated));

        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
