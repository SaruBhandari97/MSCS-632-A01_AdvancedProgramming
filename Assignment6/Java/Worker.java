public class Worker implements Runnable {
    private final TaskQueue taskQueue;

    public Worker(TaskQueue queue) {
        this.taskQueue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Task task = taskQueue.getTask();
                if (task.isPoisonPill()) {
                    System.out.println(Thread.currentThread().getName() + " received shutdown signal.");
                    break; // Exit loop
                }
                System.out.println(Thread.currentThread().getName() + " processing task: " + task.getTaskId());
                task.process();
            } catch (InterruptedException e) {
                System.err.println(Thread.currentThread().getName() + " interrupted.");
                break;
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
}
