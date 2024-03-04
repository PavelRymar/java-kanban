package test;

import main.taskManager.Managers;
import main.taskManager.TaskManager;
import main.taskType.SingleTask;
import main.taskType.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryManagersTest {
    TaskManager manager;
    @BeforeEach
    void beforeEach() {
        manager = Managers.getDefault();
    }
    @Test
    void shouldNotRepeated() {
        Task task = new SingleTask("task one", "description of task one");
        manager.saveSingleTask(task);

        for (int i = 0; i < 11; i++) {
            manager.getSingleTaskById(1);
        }

        assertEquals(1, manager.getHistory().size(), "Размер не соответствует");
    }

    @Test
    void shouldBeUnlimited() {
        for (int i = 1; i <= 150; i++) {
            manager.saveSingleTask(new SingleTask("task " + i, "description of task " + i));
            manager.getSingleTaskById(i);
        }

        assertEquals(150, manager.getHistory().size(), "Размер не соответствует");
    }



}
