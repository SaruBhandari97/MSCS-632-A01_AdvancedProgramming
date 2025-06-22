public class Task {
    private final int taskId;

    public Task(int id) {
        this.taskId = id;
    }

    public int getTaskId() {
        return taskId;
    }

    public boolean isPoisonPill() {
        return taskId == -1;
    }

    public void process() {
        try {
            Thread.sleep(500); 
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
