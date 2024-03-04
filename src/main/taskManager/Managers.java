package main.taskManager;

public class Managers {
    static TaskManager manager;
    static HistoryManager historyManager;

    public static TaskManager getDefault() {
        return manager = new InMemoryTaskManager();
    }
    static HistoryManager getDefaultHistory() {
        return historyManager = new InMemoryHistoryManager();
    }
}
