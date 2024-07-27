package com.example.demo.persistence.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

public class InformationEntity implements Serializable {
    private Integer id;

    private String title;

    private String summary;

    private String url;

    private Integer urlHashValue;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getUrlHashValue() {
        return urlHashValue;
    }

    public void setUrlHashValue(Integer urlHashValue) {
        this.urlHashValue = urlHashValue;
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
        InformationEntity other = (InformationEntity) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getSummary() == null ? other.getSummary() == null : this.getSummary().equals(other.getSummary()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getUrlHashValue() == null ? other.getUrlHashValue() == null : this.getUrlHashValue().equals(other.getUrlHashValue()))
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
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getSummary() == null) ? 0 : getSummary().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getUrlHashValue() == null) ? 0 : getUrlHashValue().hashCode());
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
        sb.append(", title=").append(title);
        sb.append(", summary=").append(summary);
        sb.append(", url=").append(url);
        sb.append(", urlHashValue=").append(urlHashValue);
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