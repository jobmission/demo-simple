package calculate;

class VTagReferenceDepth {
    private int tagId;
    private int maxDepth;

    public VTagReferenceDepth() {
    }

    public VTagReferenceDepth(int tagId, int maxDepth) {
        this.tagId = tagId;
        this.maxDepth = maxDepth;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
}

