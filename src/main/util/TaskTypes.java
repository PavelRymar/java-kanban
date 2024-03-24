package main.util;

public enum TaskTypes {
    TASK("TASK"),
    SUBTASK("SUBTASK"),
    EPIC("EPIC");

    private final String value;

    TaskTypes(String value) {
        this.value = value;
    }
    public String toString() {
        return value;
    }
}