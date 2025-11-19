package fr.insa.ms.requestMS.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import fr.insa.ms.requestMS.model.Demande;

@Repository 
public class DemandeRepository {

	@Value("${db.url}")
	private String url;
	
	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;
    
	private Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	public int createDemande(Demande d) throws SQLException {
		String sql = "INSERT INTO Demande "
				+ "(id_etudiant_demandeur, titre, description, mots_cles, date_souhaitee, statut) "
				+ "VALUES (?, ?, ?, ?, ?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			stmt.setInt(1, d.id_etudiant_demandeur);
			stmt.setString(2, d.titre);
			stmt.setString(3, d.description);
			stmt.setString(4, d.mots_cles);
			stmt.setString(5, d.date_souhaitee);
			stmt.setString(6, d.statut);

			stmt.executeUpdate();

			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1); // returns generated idDemande
			}
		}

		return -1;
	}
	
	public boolean updateDemande(Demande d) throws SQLException {
	    String sql = "UPDATE Demande SET "
	               + "id_etudiant_demandeur = ?, "
	               + "titre = ?, "
	               + "description = ?, "
	               + "mots_cles = ?, "
	               + "date_souhaitee = ?, "
	               + "statut = ? "
	               + "WHERE idDemande = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        stmt.setInt(1, d.id_etudiant_demandeur);
	        stmt.setString(2, d.titre);
	        stmt.setString(3, d.description);
	        stmt.setString(4, d.mots_cles);
	        stmt.setString(5, d.date_souhaitee);
	        stmt.setString(6, d.statut);
	        stmt.setInt(7, d.idDemande); // WHERE condition

	        int affectedRows = stmt.executeUpdate();
	        return affectedRows == 1;
	    }
	}

}
