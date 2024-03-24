package main;


import main.taskManager.*;
import main.taskType.EpicTask;
import main.taskType.SingleTask;
import main.taskType.SubTask;
import main.taskType.Task;

import java.io.File;
import java.util.List;

import static main.taskManager.FileBackedTaskManager.loadFromFile;

public class Main {
    public static void main(String[] args) {

        TaskManager fbtm = new FileBackedTaskManager(new File("input.txt"));

        System.out.println("Cоздаем задачи");
        Task task1 = new SingleTask("task 1", "description of task one");
        fbtm.saveSingleTask(task1);
        fbtm.getSingleTaskById(1);
        Task task2 = new SingleTask("task 2", "description of task two");
        fbtm.saveSingleTask(task2);
        fbtm.getSingleTaskById(2);
        Task task3 = new EpicTask("task3", "description of task 3" );
        fbtm.saveEpicTask(task3);
        fbtm.getEpicTaskById(3);

        printTasksHistory(fbtm);

        FileBackedTaskManager fileBackedTaskManager2 = loadFromFile(new File("input.txt"));
        System.out.println();
        printList(fileBackedTaskManager2);
        printTasksHistory(fileBackedTaskManager2);




//        System.out.println("Cоздадим задачи");
//        Task task = new SingleTask("task 1", "description of task one");
//        fileManager.saveSingleTask(task);
//        fileManager.getSingleTaskById(1);
//        Task task1 = new SingleTask("task 2", "description of task two");
//        fileManager.saveSingleTask(task1);
//        fileManager.getSingleTaskById(2);
//        System.out.println(fileManager.getHistory());

    }


    private static void printTasksHistory(TaskManager manager) {

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }

    private static <T extends Task> void printList(TaskManager taskManager) {
        if (!taskManager.getTasks().isEmpty()) {
            for (Task value : taskManager.getTasks().values()) {
                System.out.println(value);
            }
        } else {
            System.out.println("empty");
        }
    }
}