package main.taskManager;

import main.taskType.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    private final Map<Integer, Node> hashHistory = new HashMap<>();
    private Node head = null;
    private Node tail = null;


    @Override
    public void add(Task task) {
        int id = task.getId();
        remove(id);
        linkLast(task);
    }


    @Override
    public void remove(int id) {
        if (hashHistory.containsKey(id)) {
            removeNode(hashHistory.get(id));
            hashHistory.remove(id);
        }
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    private void linkLast(Task task) {
        Node newNode = new Node(task);
        if (tail != null) {
            newNode.setPrev(tail);
            tail.setNext(newNode);
        } else {
            head = newNode;
        }
        tail = newNode;
        hashHistory.put(task.getId(), newNode);
    }

    private List<Task> getTasks() {
        List<Task> result = new ArrayList<>();
        Node current = head;
        while (current != null) {
            result.add(current.getData());
            current = current.getNext();
        }
        return result;
    }

    private void removeNode(Node node) {
        Node next = node.getNext();
        Node prev = node.getPrev();

        if (prev != null) {
            prev.setNext(next);
        } else {
            head = next;
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            tail = prev;
        }
    }

}