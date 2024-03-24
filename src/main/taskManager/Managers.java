package main.taskManager;

public class Managers {

    public static TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

//    public static TaskManager getFileManager() {
//        return new FileBackedTaskManager(File file);
//    }
    static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
