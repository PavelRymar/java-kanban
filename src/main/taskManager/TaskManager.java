package main.taskManager;

import main.taskType.EpicTask;
import main.taskType.SubTask;
import main.taskType.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

     void saveSingleTask(Task singleTask);

     void updateSingleTask(Task singleTask);

     Task getSingleTaskById(Integer taskId);

     void deleteSingleTask(Integer taskId);

     void saveSubTask(SubTask subTask);

     void updateSubTask(SubTask subTask);
     Task getSubTaskById(Integer taskId);

     void deleteSubTaskById(Integer subTaskId);

     void saveEpicTask(Task epicTask);
     void updateEpicTask(EpicTask epicTask);
     Task getEpicTaskById(Integer epicId);

     void deleteEpicTaskById(Integer epicId);
     List<SubTask> getEpicSubTasksById(Integer epicId);
     List<Task> getAllSingleTask();
     List<Task> getAllSubtaskTask();

     List<Task> getAllEpicTask();
     void deleteAllSingleTask();
     void deleteAllSubTask();
     void deleteAllEpicTask();

     List<Task> getHistory();

}
