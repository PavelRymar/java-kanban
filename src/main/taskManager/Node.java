package main.taskManager;

import main.taskType.Task;

public class Node {
    private Task data;
    private Node prev;
    private Node next;

    public Node(Task data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public Task getData() {
        return data;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}