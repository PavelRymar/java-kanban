package main.taskType;

import main.util.Status;

public class SingleTask extends Task {

    public SingleTask(String name, String description) {
        super(name, description);
    }

    public SingleTask(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }

    @Override
    public String toString() {
        return "SingleTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
