package calculate;

import java.sql.Timestamp;

public class TagData {
    private Integer tagId;
    private Double tagValue;
    private Timestamp tagTime;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public TagData() {
    }

    public TagData(Integer tagId, Timestamp tagTime, Double tagValue) {
        this.tagId = tagId;
        this.tagTime = tagTime;
        this.tagValue = tagValue;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Double getTagValue() {
        return tagValue;
    }

    public void setTagValue(Double tagValue) {
        this.tagValue = tagValue;
    }

    public Timestamp getTagTime() {
        return tagTime;
    }

    public void setTagTime(Timestamp tagTime) {
        this.tagTime = tagTime;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
