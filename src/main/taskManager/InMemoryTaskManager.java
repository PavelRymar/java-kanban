package main.taskManager;

import main.taskType.EpicTask;
import main.taskType.SingleTask;
import main.taskType.SubTask;
import main.taskType.Task;
import main.util.Status;
import util.TaskIdGenerator;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

public class InMemoryTaskManager implements TaskManager{
    private HashMap<Integer, Task> tasks;
    private TaskIdGenerator taskIdGenerator;
    private HistoryManager historyManager;


    public InMemoryTaskManager() {
        this.tasks = new HashMap<>();
        this.taskIdGenerator = new TaskIdGenerator();
        this.historyManager = Managers.getDefaultHistory();
    }

    @Override
    public void saveSingleTask(Task singleTask) {
        singleTask.setId(taskIdGenerator.getNextFreeId());
        tasks.put(singleTask.getId(), singleTask);
    }
    @Override
    public void updateSingleTask(Task singleTask) {
        tasks.put(singleTask.getId(), singleTask);
    }
    @Override
    public Task getSingleTaskById(Integer taskId) {
        if (!tasks.containsKey(taskId)) {
            return null;
        }
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }
    @Override
    public void deleteSingleTask(Integer taskId) {
        if (tasks.containsKey(taskId)) {
            tasks.remove(taskId);
        }
        historyManager.remove(taskId);
    }
    @Override
    public void saveSubTask(SubTask subTask) {
        subTask.setId(taskIdGenerator.getNextFreeId());
        tasks.put(subTask.getId(), subTask);

        int epicId = subTask.getEpicId();
        EpicTask epicTask = (EpicTask) tasks.get(epicId);

        List<SubTask> list = epicTask.getSubTasks();

        list.add(subTask);

        epicTask.setSubTasks(list);
        updateEpicTask(epicTask);
    }
    @Override
    public void updateSubTask(SubTask subTask) {
        tasks.put(subTask.getId(), subTask);

        int epicId = subTask.getEpicId();
        EpicTask epic = (EpicTask) tasks.get(epicId);

        calculateEpicStatus(epic);
    }

    private void calculateEpicStatus(EpicTask epic) {

        List<Status> statuses = new ArrayList<>();

        for (SubTask task : epic.getSubTasks()) {
            statuses.add(task.getStatus());
        }

        if (statuses.isEmpty()) {
            epic.setStatus(Status.NEW);
        }


        if (statuses.contains(Status.NEW) &&
                !statuses.contains(Status.IN_PROGRESS) && !statuses.contains(Status.DONE)) {
            epic.setStatus(Status.NEW);
        } else if (statuses.contains(Status.DONE) &&
                !statuses.contains(Status.NEW) && !statuses.contains(Status.IN_PROGRESS)) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }

        updateEpicTask(epic);
    }
    @Override
    public Task getSubTaskById(Integer taskId) {
        if (!tasks.containsKey(taskId)) {
            System.out.println("Такой сабтаски нет");
            return null;
        }
        historyManager.add(tasks.get(taskId));
        return tasks.get(taskId);
    }
    @Override
    public void deleteSubTaskById(Integer subTaskId) {
        if (tasks.containsKey(subTaskId)) {
            SubTask subTask = (SubTask) tasks.get(subTaskId);
            int epicId = subTask.getEpicId();

            EpicTask epicTask = (EpicTask) tasks.get(epicId);

            tasks.remove(subTaskId);

            List<SubTask> list = epicTask.getSubTasks();
            list.remove(subTask);

            epicTask.setSubTasks(list);
            calculateEpicStatus(epicTask);

        } else {
            System.out.printf("Сабтаски с id = %s нет в базе", subTaskId);
        }
        historyManager.remove(subTaskId);

    }
    @Override

    public void saveEpicTask(Task epicTask) {
        epicTask.setId(taskIdGenerator.getNextFreeId());
        tasks.put(epicTask.getId(), epicTask);
    }
    @Override
    public void updateEpicTask(EpicTask epicTask) {
        tasks.put(epicTask.getId(), epicTask);
    }
    @Override
    public Task getEpicTaskById(Integer epicId) {
        if (!tasks.containsKey(epicId)) {
            return null;
        }
        historyManager.add(tasks.get(epicId));
        return tasks.get(epicId);

    }
    @Override
    public void deleteEpicTaskById(Integer epicId) {

        EpicTask epic = (EpicTask) tasks.get(epicId);

        List<SubTask> subTasks = epic.getSubTasks();

        for (SubTask subTask : subTasks) {
            if (tasks.containsKey(subTask.getId())) {
                tasks.remove(subTask.getId());
            }

        }

        if (tasks.containsKey(epicId)) {
            tasks.remove(epicId);
        }
        historyManager.remove(epicId);
    }
    @Override
    public List<SubTask> getEpicSubTasksById(Integer epicId) {
        if (!tasks.containsKey(epicId)) {
            return null;
        }

        EpicTask epicTask = (EpicTask) tasks.get(epicId);

        return epicTask.getSubTasks();
    }
    @Override
    public List<Task> getAllSingleTask() {
        List<Task> allTasks = new ArrayList<>();

        for (Task value : tasks.values()) {
            if (value instanceof SingleTask) {
                allTasks.add(value);
            }
        }
        return allTasks;
    }
    @Override
    public List<Task> getAllSubtaskTask() {
        List<Task> allTasks = new ArrayList<>();

        for (Task value : tasks.values()) {
            if (value instanceof SubTask) {
                allTasks.add(value);
            }
        }
        return allTasks;
    }
    @Override
    public List<Task> getAllEpicTask() {
        List<Task> allTasks = new ArrayList<>();

        for (Task value : tasks.values()) {
            if (value instanceof EpicTask) {
                allTasks.add(value);
            }
        }
        return allTasks;
    }
    @Override
    public void deleteAllSingleTask() {

        for (Task value : tasks.values()) {
            if (value instanceof SingleTask) {
                tasks.remove(value.getId());
            }
        }
    }
    @Override
    public void deleteAllSubTask() {

        for (Task value : tasks.values()) {
            if (value instanceof SubTask) {
                tasks.remove(value.getId());
            }
        }
    }
    @Override
    public void deleteAllEpicTask() {

        for (Task value : tasks.values()) {
            if (value instanceof SubTask || value instanceof EpicTask) {
                tasks.remove(value.getId());
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }

    @Override
    public String toString() {
        return "taskManager.TaskManager{" +
                "tasks=" + tasks +
                '}';
    }
}

