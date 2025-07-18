package calculate;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CycleDetector {
    private final VTagDAO vTagDAO;

    public CycleDetector(VTagDAO vTagDAO) {
        this.vTagDAO = vTagDAO;
    }

    public boolean hasCircularDependency(String vTagName, Integer vtagId, String formula) throws SQLException {
        if (vTagName != null && !vTagName.isBlank()) {
            Integer dbId = vTagDAO.getVTagIdByName(vTagName);
            if (dbId != null) {
                vtagId = dbId;
            }
        }
        if (vtagId == null) {
            return false;
        }
        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<TagDependency> dependencyList = vTagDAO.getDependencies();
        for (TagDependency dependency : dependencyList) {
            graph.computeIfAbsent(dependency.getTagId(), k -> new ArrayList<>())
                .add(dependency.getDependsOnTagId());
        }
        if (formula != null && !formula.isBlank()) {
            Set<Integer> deps = extractDependencies(formula);
            for (Integer dep : deps) {
                graph.computeIfAbsent(vtagId, k -> new ArrayList<>()).add(dep);
            }
        }
        Set<Integer> visited = new HashSet<>();
        Set<Integer> recursionStack = new HashSet<>();
        return dfs(graph, vtagId, visited, recursionStack);
    }

    private boolean dfs(Map<Integer, List<Integer>> graph, int tagId, Set<Integer> visited, Set<Integer> recursionStack) {
        if (recursionStack.contains(tagId)) return true;
        if (visited.contains(tagId)) return false;

        visited.add(tagId);
        recursionStack.add(tagId);

        for (int neighbor : graph.getOrDefault(tagId, new ArrayList<>())) {
            if (dfs(graph, neighbor, visited, recursionStack)) return true;
        }
        recursionStack.remove(tagId);
        return false;
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
}
