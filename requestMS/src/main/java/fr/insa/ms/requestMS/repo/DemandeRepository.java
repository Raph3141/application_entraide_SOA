package fr.insa.ms.requestMS.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import fr.insa.ms.requestMS.model.Day;
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

//	public int createDemande(Demande d) throws SQLException {
//		String sql = "INSERT INTO Demande "
//				+ "(id_etudiant_demandeur, titre, description, mots_cles, date_souhaitee, statut) "
//				+ "VALUES (?, ?, ?, ?, ?, ?)";
//
//		try (Connection conn = getConnection();
//				PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
//
//			stmt.setInt(1, d.id_etudiant_demandeur);
//			stmt.setString(2, d.titre);
//			stmt.setString(3, d.description);
//			stmt.setString(4, d.mots_cles);
//	        stmt.setString(5, d.date_souhaitee != null ? d.date_souhaitee.name() : null);
//			stmt.setString(6, d.statut);
//
//			stmt.executeUpdate();
//
//			ResultSet rs = stmt.getGeneratedKeys();
//			if (rs.next()) {
//				return rs.getInt(1); // returns generated idDemande
//			}
//		}
//
//		return -1;
//	}
//	
//	public boolean updateDemande(Demande d) throws SQLException {
//	    String sql = "UPDATE Demande SET "
//	               + "id_etudiant_demandeur = ?, "
//	               + "titre = ?, "
//	               + "description = ?, "
//	               + "mots_cles = ?, "
//	               + "date_souhaitee = ?, "
//	               + "statut = ? "
//	               + "WHERE idDemande = ?";
//
//	    try (Connection conn = getConnection();
//	         PreparedStatement stmt = conn.prepareStatement(sql)) {
//
//	        stmt.setInt(1, d.id_etudiant_demandeur);
//	        stmt.setString(2, d.titre);
//	        stmt.setString(3, d.description);
//	        stmt.setString(4, d.mots_cles);
//	        stmt.setString(5, d.date_souhaitee != null ? d.date_souhaitee.name() : null);
//	        stmt.setString(6, d.statut);
//	        stmt.setInt(7, d.idDemande); // WHERE condition
//
//	        int affectedRows = stmt.executeUpdate();
//	        return affectedRows == 1;
//	    }
//	}
	
	 // -------------------- CREATE --------------------
    public int createDemande(Demande d) throws SQLException {
        String sql = "INSERT INTO Demande (id_etudiant_demandeur, id_etudiant_tuteur, titre, description, mots_cles, date_souhaitee, statut) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, d.id_etudiant_demandeur);
            if (d.id_etudiant_tuteur != null) {
                stmt.setInt(2, d.id_etudiant_tuteur);
            } else {
            	stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setString(3, d.titre);
            stmt.setString(4, d.description);
            stmt.setString(5, d.mots_cles);
            stmt.setString(6, d.date_souhaitee != null ? d.date_souhaitee.name() : null);
            stmt.setString(7, d.statut);

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // returns generated idDemande
            }
        }
        return -1;
    }

    // -------------------- DELETE --------------------
    public boolean deleteDemande(int idDemande) throws SQLException {
        String sql = "DELETE FROM Demande WHERE idDemande = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDemande);
            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1;
        }
    }

    // -------------------- UPDATE --------------------
    public boolean updateDemande(Demande d) throws SQLException {
        String sql = "UPDATE Demande SET "
                   + "id_etudiant_demandeur = ?, "
                   + "id_etudiant_tuteur = ?, "
                   + "titre = ?, "
                   + "description = ?, "
                   + "mots_cles = ?, "
                   + "date_souhaitee = ?, "
                   + "statut = ? "
                   + "WHERE idDemande = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, d.id_etudiant_demandeur);
            stmt.setInt(2, d.id_etudiant_tuteur);
            stmt.setString(3, d.titre);
            stmt.setString(4, d.description);
            stmt.setString(5, d.mots_cles);
            stmt.setString(6, d.date_souhaitee != null ? d.date_souhaitee.name() : null);
            stmt.setString(7, d.statut);
            stmt.setInt(8, d.idDemande);

            int affectedRows = stmt.executeUpdate();
            return affectedRows == 1;
        }
    }

    // -------------------- GET BY ID --------------------
    public Demande getDemandeById(int idDemande) throws SQLException {
        String sql = "SELECT * FROM Demande WHERE idDemande = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDemande);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToDemande(rs);
            }
        }
        return null;
    }

    // -------------------- GET ALL --------------------
    public List<Demande> getAllDemandes() throws SQLException {
        String sql = "SELECT * FROM Demande";
        List<Demande> demandes = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                demandes.add(mapResultSetToDemande(rs));
            }
        }
        return demandes;
    }

    // -------------------- HELPER --------------------
    private Demande mapResultSetToDemande(ResultSet rs) throws SQLException {
        Demande d = new Demande();
        d.idDemande = rs.getInt("idDemande");
        d.id_etudiant_demandeur = rs.getInt("id_etudiant_demandeur");
        d.id_etudiant_tuteur = rs.getInt("id_etudiant_tuteur");
        d.titre = rs.getString("titre");
        d.description = rs.getString("description");
        d.mots_cles = rs.getString("mots_cles");

        String jour = rs.getString("date_souhaitee");
        d.date_souhaitee = jour != null ? Day.valueOf(jour) : null;

        d.statut = rs.getString("statut");

        return d;
    }

}
