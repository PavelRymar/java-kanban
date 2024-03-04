package main.taskManager;

import main.taskType.EpicTask;
import main.taskType.SubTask;
import main.taskType.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    public void saveSingleTask(Task singleTask);

    public void updateSingleTask(Task singleTask);

    public Task getSingleTaskById(Integer taskId);

    public void deleteSingleTask(Integer taskId);

    public void saveSubTask(SubTask subTask);

    public void updateSubTask(SubTask subTask);
    public Task getSubTaskById(Integer taskId);

    public void deleteSubTaskById(Integer subTaskId);

    public void saveEpicTask(Task epicTask);
    public void updateEpicTask(EpicTask epicTask);
    public Task getEpicTaskById(Integer epicId);

    public void deleteEpicTaskById(Integer epicId);
    public List<SubTask> getEpicSubTasksById(Integer epicId);
    public List<Task> getAllSingleTask();
    public List<Task> getAllSubtaskTask();

    public List<Task> getAllEpicTask();
    public void deleteAllSingleTask();
    public void deleteAllSubTask();
    public void deleteAllEpicTask();
    public ArrayList<Task> getTaskById(List<Integer> taskIds);

    public List<Task> getHistory();

}
