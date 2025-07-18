package calculate;

public class TagDependency {
    private Integer tagId;
    private Integer dependsOnTagId;

    public TagDependency(Integer tagId, Integer dependsOnTagId) {
        this.tagId = tagId;
        this.dependsOnTagId = dependsOnTagId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getDependsOnTagId() {
        return dependsOnTagId;
    }

    public void setDependsOnTagId(Integer dependsOnTagId) {
        this.dependsOnTagId = dependsOnTagId;
    }
}
