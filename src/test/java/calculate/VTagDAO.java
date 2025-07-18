package calculate;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VTagDAO {

    public boolean exists(int tagId) throws SQLException {
        String sql = "SELECT id FROM vtag WHERE id = ?";
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

    public Integer addVTag(VTag vTag) throws SQLException {
        String sql = "INSERT INTO vtag(name, formula, description) VALUES(?, ?, ?) ON CONFLICT (name) DO UPDATE SET formula = ?, description = ?, updated_at = CURRENT_TIMESTAMP";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, vTag.getName());
            pstmt.setString(2, vTag.getFormula());
            pstmt.setString(3, vTag.getDescription());
            pstmt.setString(4, vTag.getFormula());
            pstmt.setString(5, vTag.getDescription());
            pstmt.executeUpdate();
            Integer vtagId = null;
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                vtagId = rs.getInt(1);
            }
            // 2. 解析并存储依赖
            Set<Integer> deps = extractDependencies(vTag.getFormula());
            for (Integer depId : deps) {
                VTag vTagTemp = getVTag(depId);
                if (vTagTemp != null) {
                    addDependency(vtagId, depId);
                }
            }
            return vtagId;
        }
    }

    public Integer getVTagIdByName(String name) throws SQLException {
        String sql = "SELECT id FROM vtag WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        }
        return null;
    }

    public VTag getVTag(int id) throws SQLException {
        String sql = "SELECT * FROM vtag WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new VTag(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("formula"),
                    rs.getString("description")
                );
            }
        }
        return null;
    }

    public List<VTag> getAllVTags() throws SQLException {
        List<VTag> vTags = new ArrayList<>();
        String sql = "SELECT * FROM vtag";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vTags.add(new VTag(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("formula"),
                    rs.getString("description")
                ));
            }
        }
        return vTags;
    }

    public void updateVTag(VTag vTag) throws SQLException {
        String sql = "UPDATE vtag SET name = ?, formula = ?, description = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, vTag.getName());
            pstmt.setString(2, vTag.getFormula());
            pstmt.setString(3, vTag.getDescription());
            pstmt.setInt(4, vTag.getId());
            pstmt.executeUpdate();

            int vtagId = vTag.getId();
            clearDependencies(vtagId);
            // 2. 解析并存储依赖
            Set<Integer> deps = extractDependencies(vTag.getFormula());
            for (Integer depId : deps) {
                VTag vTagTemp = getVTag(depId);
                if (vTagTemp != null) {
                    addDependency(vtagId, depId);
                }
            }
        }
    }

    public void deleteVTag(int id) throws SQLException {
        String sql = "DELETE FROM vtag WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }

    // 新增方法：添加依赖关系
    public void addDependency(Integer vtagId, int dependsOnId) throws SQLException {
        String sql = "INSERT INTO vtag_dependency(vtag_id, depends_on_tag_id) VALUES(?, ?) on CONFLICT (vtag_id, depends_on_tag_id) DO NOTHING";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vtagId);
            pstmt.setInt(2, dependsOnId);
            pstmt.executeUpdate();
        }
    }

    // 新增方法：获取所有依赖的VTag
    public List<Integer> getDependencies(int vtagId) throws SQLException {
        List<Integer> dependencies = new ArrayList<>();
        String sql = "SELECT depends_on_tag_id FROM vtag_dependency WHERE vtag_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vtagId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dependencies.add(rs.getInt(1));
            }
        }
        return dependencies;
    }

    public List<TagDependency> getDependencies() throws SQLException {
        List<TagDependency> dependencyList = new ArrayList<>();
        String sql = "SELECT vtag_id,depends_on_tag_id FROM vtag_dependency";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                dependencyList.add(new TagDependency(rs.getInt("vtag_id"), rs.getInt("depends_on_tag_id")));
            }
        }
        return dependencyList;
    }

    // 新增方法：删除所有依赖关系
    public void clearDependencies(int vtagId) throws SQLException {
        String sql = "DELETE FROM vtag_dependency WHERE vtag_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, vtagId);
            pstmt.executeUpdate();
        }
    }

    private Set<Integer> extractDependencies(String formula) {
        // 使用正则提取变量标识符
        Pattern vtagPattern = Pattern.compile("vtag\\|(\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher m = vtagPattern.matcher(formula);
        Set<Integer> deps = new HashSet<>();
        while (m.find()) {
            deps.add(Integer.parseInt(m.group(1)));
        }
        return deps;
    }

    public List<Integer> getNotReferencedVTagIds() throws SQLException {
        List<Integer> notReferencedVTagIds = new ArrayList<>();
        String sql = """
            SELECT id FROM vtag
            WHERE id NOT IN (
                SELECT DISTINCT vtag_id FROM vtag_dependency
            )
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                notReferencedVTagIds.add(rs.getInt("id"));
            }
        }
        return notReferencedVTagIds;
    }

    public List<VTagReferenceDepth> getReferenceDepth() throws SQLException {
        List<VTagReferenceDepth> vTagReferenceDepthList = new ArrayList<>();
        String sql = """
            WITH RECURSIVE dependency_tree AS (
                -- 基础查询：找出所有叶子节点（未被引用的末端节点，深度为0）
                SELECT
                    vtag_id,
                    depends_on_tag_id,
                    0 AS depth,
                    ARRAY[vtag_id] AS path  -- 路径追踪防循环
                FROM vtag_dependency
                WHERE NOT EXISTS (
                    SELECT 1 FROM vtag_dependency
                    WHERE depends_on_tag_id = vtag_dependency.vtag_id
                )
                UNION ALL
                -- 递归部分：向上计算依赖链深度
                SELECT
                    vd.vtag_id,
                    vd.depends_on_tag_id,
                    dt.depth + 1,
                    dt.path || vd.vtag_id
                FROM vtag_dependency vd
                JOIN dependency_tree dt ON vd.depends_on_tag_id = dt.vtag_id
                WHERE NOT vd.vtag_id = ANY(dt.path)  -- 阻断循环依赖
            )
            SELECT
                vtag_id,
                MAX(depth) AS max_depth
            FROM dependency_tree
            GROUP BY vtag_id
            ORDER BY max_depth ASC,vtag_id ASC
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                vTagReferenceDepthList.add(new VTagReferenceDepth(
                    rs.getInt("vtag_id"),
                    rs.getInt("max_depth")
                ));
            }
        }
        return vTagReferenceDepthList;
    }
}

