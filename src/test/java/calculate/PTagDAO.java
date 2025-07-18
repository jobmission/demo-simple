package calculate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PTagDAO {

    public boolean exists(int tagId) throws SQLException {
        String sql = "SELECT id FROM ptag WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tagId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            }
        }
        return false;
    }

    public Integer addPTag(PTag pTag) throws SQLException {
        String sql = "INSERT INTO ptag(name, description) VALUES(?, ?) on conflict (name) do update set description = ?, updated_at = CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, pTag.getName());
            pstmt.setString(2, pTag.getDescription());
            pstmt.setString(3, pTag.getDescription());
            pstmt.executeUpdate();
            Integer generatedId= null;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            return generatedId;
        }
    }

    public PTag getPTag(int id) throws SQLException {
        String sql = "SELECT * FROM ptag WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new PTag(
                    rs.getString("name"),
                    rs.getString("description")
                );
            }
        }
        return null;
    }

    public List<PTag> getAllPTags() throws SQLException {
        List<PTag> pTags = new ArrayList<>();
        String sql = "SELECT * FROM ptag";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                pTags.add(new PTag(
                    rs.getString("name"),
                    rs.getString("description")
                ));
            }
        }
        return pTags;
    }

    public void updatePTag(PTag pTag) throws SQLException {
        String sql = "UPDATE ptag SET name = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, pTag.getName());
            pstmt.setString(2, pTag.getDescription());
            pstmt.setInt(3, pTag.getId());
            pstmt.executeUpdate();
        }
    }

    public void deletePTag(int id) throws SQLException {
        String sql = "DELETE FROM ptag WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}

