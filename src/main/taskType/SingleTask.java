package main.taskType;

public class SingleTask extends Task {

    public SingleTask(String name, String description) {
        super(name, description);
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
