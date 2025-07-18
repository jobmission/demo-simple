package calculate;

import java.sql.*;

public class VTagValueDAO {
    public void addTagValue(TagData tagData) throws SQLException {
        String sql = "INSERT INTO vtag_value(tag_id, tag_time, tag_value) VALUES(?, ?, ?) on conflict (tag_id, tag_time) do update set tag_value = ?, updated_at = CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, tagData.getTagId());
            pstmt.setTimestamp(2, tagData.getTagTime());
            if (tagData.getTagValue() != null) {
                pstmt.setDouble(3, tagData.getTagValue());
                pstmt.setDouble(4, tagData.getTagValue());
            } else {
                pstmt.setNull(3, Types.DOUBLE);
                pstmt.setNull(4, Types.DOUBLE);
            }

            pstmt.executeUpdate();
        }
    }

    public TagData getTagValue(int tagId, Timestamp tagTime) throws SQLException {
        String sql = "SELECT * FROM vtag_value WHERE tag_id = ? and tag_time = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tagId);
            pstmt.setTimestamp(2, tagTime);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TagData(
                    rs.getInt("tag_id"),
                    rs.getTimestamp("tag_time"),
                    rs.getDouble("tag_value")
                );
            }
        }
        return null;
    }

}

