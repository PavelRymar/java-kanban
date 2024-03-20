package main;

import main.taskManager.Managers;
import main.taskManager.TaskManager;
import main.taskType.EpicTask;
import main.taskType.SingleTask;
import main.taskType.SubTask;
import main.taskType.Task;

import java.util.List;

public class Main {
    public static void main(String[] args) {

       TaskManager inMemoryTaskManager = Managers.getDefault();

        System.out.println("Cоздадим задачи");
        Task task = new SingleTask("task 1", "description of task one");
        inMemoryTaskManager.saveSingleTask(task);
        inMemoryTaskManager.getSingleTaskById(1);
        Task task1 = new SingleTask("task 2", "description of task two");
        inMemoryTaskManager.saveSingleTask(task1);
        inMemoryTaskManager.getSingleTaskById(2);
        System.out.println(inMemoryTaskManager.getHistory());
    }


    private static void printAllTasks(TaskManager manager) {

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

    private static <T extends Task> void printList(List<T> taskList) {
        if (!taskList.isEmpty()) {
            for (T task : taskList) {
                System.out.println(task);
            }
        } else {
            System.out.println("empty");
        }
    }
}