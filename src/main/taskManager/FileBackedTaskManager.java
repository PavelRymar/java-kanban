package main.taskManager;

import exceptions.ManagerSaveException;
import main.taskType.*;
import main.util.Status;

import java.io.*;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static main.taskType.TaskType.TASK;


public class FileBackedTaskManager extends InMemoryTaskManager implements TaskManager {
    private File savedFile;

    public FileBackedTaskManager(File inputFile) {
        this.savedFile = inputFile;
    }

    public void save()  {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(savedFile, StandardCharsets.UTF_8))) {

            StringBuilder history = new StringBuilder();
            history.append("id,type,name,status,description,epic\n");

            List<Task> allSingleTask =  getAllSingleTask();
            for (Task task : allSingleTask) {
                history.append(toStringTask((SingleTask) task)).append("\n");
            }

            List<Task> allSubtaskTask =  getAllSubtaskTask();
            for (Task task: allSubtaskTask) {
                history.append(toStringSubTask((SubTask) task)).append("\n");

            }

            List<Task> allEpicTask =  getAllEpicTask();
            for (Task task: allEpicTask) {
                history.append(toStringEpicTask((EpicTask) task)).append("\n");
            }

            history.append("\n");

            if (getHistory().isEmpty())
                history.append("history is empty");
            else {
               history.append(historyToString(getHistoryManager()));
            }

            writer.write(history.toString());
            writer.flush();

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка работы с файлом");
        }
    }


    private String toStringTask(Task task){
        return String.format("%s,%s,%s,%s,%s",
                String.valueOf (task.getId()), (TASK), task.getName(),String.valueOf(task.getStatus()),task.getDescription());
    }

    private String toStringSubTask(SubTask subTask){
        return String.format("%s,%s,%s,%s,%s,%s",
                String.valueOf (subTask.getId()),(TaskType.SUBTASK), subTask.getName(),String.valueOf(subTask.getStatus()),subTask.getDescription(), subTask.getEpicId());
    }
    private String toStringEpicTask(EpicTask epicTask){
        return String.format("%s,%s,%s,%s,%s",
                String.valueOf (epicTask.getId()),(TaskType.EPICTASK), epicTask.getName(),String.valueOf(epicTask.getStatus()),epicTask.getDescription());
    }


    public static Task fromString(String value) {
        String[] split = value.split(",");
        int id = Integer.parseInt(split[0]);
        TaskType taskType = TaskType.valueOf(split[1]);
        String name = split[2];
        Status status = Status.valueOf(split[3]);
        String description = split[4];

        Task task = null;

        switch (taskType) {
            case TASK:
                task = new SingleTask(id, name, description,status);
                break;
            case SUBTASK:
                int epicId = Integer.parseInt(split[5]);
                task = new SubTask(id, name, description, status, epicId);
                break;
            case EPICTASK:
                task = new EpicTask(id, name, description, status);
                break;
        }

        return task;
    }

    public static FileBackedTaskManager loadFromFile(File file) throws ManagerSaveException {
        try {
            // считываем целиком текст из файла в строковую переменную
            String data = Files.readString(file.toPath());
            // создаем на основе файла массив строк
            String[] lines = data.split("\n");
            FileBackedTaskManager fileBackedTaskManager = new FileBackedTaskManager(file);
            int size = lines.length;

            for (int i = 1; i < size - 2; i++) {
                Task task = fromString(lines[i]);
                if (task instanceof EpicTask) {
                    fileBackedTaskManager.getTasks().put(task.getId(), task);
                } else if (task instanceof SubTask) {
                    fileBackedTaskManager.getTasks().put(task.getId(), task);
                } else {
                    fileBackedTaskManager.getTasks().put(task.getId(), task);
                }
            }

            List<Integer> history = historyFromString(lines[size - 1]);
            if (!history.isEmpty()) {
                for (int idTask : history) {
                    if (fileBackedTaskManager.getTasks().containsKey(idTask)) {
                        for (Task value : fileBackedTaskManager.getTasks().values()) {
                            if (value instanceof SingleTask) {
                                fileBackedTaskManager.getHistoryManager().add(fileBackedTaskManager.getSingleTaskById(idTask));
                            }

                            if (value instanceof SubTask) {
                                fileBackedTaskManager.getHistoryManager().add(fileBackedTaskManager.getSubTaskById(idTask));
                            }

                            if (value instanceof EpicTask) {
                                fileBackedTaskManager.getHistoryManager().add(fileBackedTaskManager.getEpicTaskById(idTask));
                            }
                        }
                    }
                }
            }
            return fileBackedTaskManager;
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка работы с файлом");
        }
    }

     public static String historyToString(HistoryManager manager){
         StringBuilder result = new StringBuilder();
         int i = 0;
         for (Task task : manager.getHistory()) {
             if (i == 0) {
                 result.append(task.getId());
             } else {
                 result.append(",").append(task.getId());
             }

             i++;
         }

         return result.toString();

    }

    public static List<Integer> historyFromString(String value) {
        List<Integer> result = new ArrayList<>();
        if (value.equals("history is empty")) {
            return result;
        }

        String[] historyIds = value.split(",");
        for (String id : historyIds) {
            result.add(Integer.parseInt(id));
        }

        return result;

    }

    @Override
    public void saveSingleTask(Task singleTask) {
        super.saveSingleTask(singleTask);
        save();
    }

    @Override
    public void updateSingleTask(Task singleTask) {
        super.updateSingleTask(singleTask);
        save();
    }

    @Override
    public Task getSingleTaskById(Integer taskId) {
        save();
        return super.getSingleTaskById(taskId);


    }

    @Override
    public void deleteSingleTask(Integer taskId) {
        super.deleteSingleTask(taskId);
        save();
    }

    @Override
    public void saveSubTask(SubTask subTask) {
        super.saveSubTask(subTask);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    @Override
    public Task getSubTaskById(Integer taskId) {
        save();
        return super.getSubTaskById(taskId);
    }

    @Override
    public void deleteSubTaskById(Integer subTaskId) {
        super.deleteSubTaskById(subTaskId);
        save();
    }

    @Override
    public void saveEpicTask(Task epicTask) {
        super.saveEpicTask(epicTask);
        save();
    }

    @Override
    public void updateEpicTask(EpicTask epicTask) {
        super.updateEpicTask(epicTask);
        save();
    }

    @Override
    public Task getEpicTaskById(Integer epicId) {
        save();
        return super.getEpicTaskById(epicId);
    }

    @Override
    public void deleteEpicTaskById(Integer epicId) {
        super.deleteEpicTaskById(epicId);
        save();
    }

    @Override
    public List<SubTask> getEpicSubTasksById(Integer epicId) {
        return super.getEpicSubTasksById(epicId);
    }

    @Override
    public List<Task> getAllSingleTask() {
        return super.getAllSingleTask();
    }

    @Override
    public List<Task> getAllSubtaskTask() {
        return super.getAllSubtaskTask();
    }

    @Override
    public List<Task> getAllEpicTask() {
        return super.getAllEpicTask();
    }

    @Override
    public void deleteAllSingleTask() {
        super.deleteAllSingleTask();
        save();
    }

    @Override
    public void deleteAllSubTask() {
        super.deleteAllSubTask();
        save();
    }

    @Override
    public void deleteAllEpicTask() {
        super.deleteAllEpicTask();
        save();
    }

    @Override
    public List<Task> getHistory() {
        return super.getHistory();
    }

    @Override
    public HistoryManager getHistoryManager() {
        return super.getHistoryManager();
    }
}