package calculate;

import java.sql.Timestamp;

class VTag {
    private Integer id;
    private String name;
    private String formula;
    private String description;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    // 构造方法、getter和setter
    public VTag() {
    }

    public VTag(String name, String formula, String description) {
        this.name = name;
        this.formula = formula;
        this.description = description;
    }

    public VTag(Integer id, String name, String formula, String description) {
        this.id = id;
        this.name = name;
        this.formula = formula;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

