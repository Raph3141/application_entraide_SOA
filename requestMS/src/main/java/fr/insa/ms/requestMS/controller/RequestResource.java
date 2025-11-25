package fr.insa.ms.requestMS.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.insa.ms.requestMS.model.Request;
import fr.insa.ms.requestMS.repo.RequestRepository;

@RestController
@RequestMapping("/requests")
public class RequestResource {

	private final RequestRepository repo;

	public RequestResource(RequestRepository repo) {
		this.repo = repo;
	}

	@PostMapping
	public ResponseEntity<?> createRequest(@RequestBody Request newRequest) {
		try {
			Integer id = repo.createRequest(newRequest);

			if (id == -1) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("error", "Failed to insert demande"));
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idDemande", id));

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	@PutMapping
	public ResponseEntity<?> updateRequest(@RequestBody Request demande) {
		try {
			boolean updated = repo.updateRequest(demande);

			if (!updated) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
						.body(Map.of("error", "Failed to update demande"));
			}

			return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("updated", updated));

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteRequest(@PathVariable Integer id) {
		try {
			boolean deleted = repo.deleteRequest(id);

			if (!deleted) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND)
						.body(Map.of("error", "Request not found or could not be deleted"));
			}

			return ResponseEntity.ok(Map.of("deleted", true));

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getRequestById(@PathVariable Integer id) {
		try {
			Request demande = repo.getRequestById(id);

			if (demande == null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Request not found"));
			}

			return ResponseEntity.ok(demande);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllRequests() {
		try {
			List<Request> demandes = repo.getAllRequests();
			return ResponseEntity.ok(demandes);

		} catch (SQLException e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", e.getMessage()));
		}
	}
}
