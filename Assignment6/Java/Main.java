public class Main {
    public static void main(String[] args) {
        TaskQueue queue = new TaskQueue();

        // Add 10 regular tasks
        for (int i = 1; i <= 10; i++) {
            queue.addTask(new Task(i));
        }

        int numWorkers = 3;
        Thread[] workers = new Thread[numWorkers];

        // Start worker threads
        for (int i = 0; i < numWorkers; i++) {
            workers[i] = new Thread(new Worker(queue), "Worker-" + i);
            workers[i].start();
        }

        // Add poison pills to stop threads
        for (int i = 0; i < numWorkers; i++) {
            queue.addTask(new Task(-1)); 
        }

        // Wait for all workers to finish
        for (Thread worker : workers) {
            try {
                worker.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrupted: " + e.getMessage());
            }
        }

        System.out.println("✅ All tasks processed.");
    }
}
