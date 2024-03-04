package util;

public class TaskIdGenerator {
    private int nextFreeId = 0;

    public int getNextFreeId() {
        return ++nextFreeId;
    }
}
