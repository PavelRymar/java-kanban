package main.taskType;

import java.util.ArrayList;
import java.util.List;
public class EpicTask extends Task {
    private List<SubTask> subTasks;


    public EpicTask(String name, String description) {
        super(name, description);
        this.subTasks = new ArrayList<>();
    }

    public List<SubTask> getSubTasks() {
        return subTasks;
    }

    public void setSubTasks(List<SubTask> subTasks) {
        this.subTasks = subTasks;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", subTasks=" + subTasks +
                '}';
    }
}
