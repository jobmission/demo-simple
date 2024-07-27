package com.example.demo.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SequenceEntity implements Serializable {
    private Integer id;

    private String sequenceName;

    private Long currentValue;

    private Integer version;

    private Integer recordStatus;

    private Integer sortPriority;

    private String remark;

    private LocalDateTime dateCreated;

    private LocalDateTime lastModified;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSequenceName() {
        return sequenceName;
    }

    public void setSequenceName(String sequenceName) {
        this.sequenceName = sequenceName == null ? null : sequenceName.trim();
    }

    public Long getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Long currentValue) {
        this.currentValue = currentValue;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getSortPriority() {
        return sortPriority;
    }

    public void setSortPriority(Integer sortPriority) {
        this.sortPriority = sortPriority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SequenceEntity other = (SequenceEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSequenceName() == null ? other.getSequenceName() == null : this.getSequenceName().equals(other.getSequenceName()))
            && (this.getCurrentValue() == null ? other.getCurrentValue() == null : this.getCurrentValue().equals(other.getCurrentValue()))
            && (this.getVersion() == null ? other.getVersion() == null : this.getVersion().equals(other.getVersion()))
            && (this.getRecordStatus() == null ? other.getRecordStatus() == null : this.getRecordStatus().equals(other.getRecordStatus()))
            && (this.getSortPriority() == null ? other.getSortPriority() == null : this.getSortPriority().equals(other.getSortPriority()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getDateCreated() == null ? other.getDateCreated() == null : this.getDateCreated().equals(other.getDateCreated()))
            && (this.getLastModified() == null ? other.getLastModified() == null : this.getLastModified().equals(other.getLastModified()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSequenceName() == null) ? 0 : getSequenceName().hashCode());
        result = prime * result + ((getCurrentValue() == null) ? 0 : getCurrentValue().hashCode());
        result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
        result = prime * result + ((getRecordStatus() == null) ? 0 : getRecordStatus().hashCode());
        result = prime * result + ((getSortPriority() == null) ? 0 : getSortPriority().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getDateCreated() == null) ? 0 : getDateCreated().hashCode());
        result = prime * result + ((getLastModified() == null) ? 0 : getLastModified().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", sequenceName=").append(sequenceName);
        sb.append(", currentValue=").append(currentValue);
        sb.append(", version=").append(version);
        sb.append(", recordStatus=").append(recordStatus);
        sb.append(", sortPriority=").append(sortPriority);
        sb.append(", remark=").append(remark);
        sb.append(", dateCreated=").append(dateCreated);
        sb.append(", lastModified=").append(lastModified);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}