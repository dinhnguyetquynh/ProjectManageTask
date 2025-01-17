package model;

public enum Priority {
	LOW("Thấp"), MEDIUM("Trung bình"), HIGH("Cao");

    private String priority;

    private Priority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority;
    }
}
