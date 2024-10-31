package main.br.com.fantasticpalmtree.threads;

import java.util.Queue;

public class WorkerThread extends Thread {
    private final Queue<Runnable> taskQueue;
    private volatile boolean isStopped;

    public WorkerThread(Queue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
        this.isStopped = false;
    }

    public void run() {
        while (!isStopped) {
            Runnable task;

            synchronized (taskQueue) {
                while (taskQueue.isEmpty()) {
                    try {
                        taskQueue.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                task = taskQueue.poll();
            }

            if (task != null) {
                try {
                    task.run();
                } catch (RuntimeException e) {
                    System.out.println("Thread execution error: " + e.getMessage());
                }
            }
        }
    }

    public void doStop() {
        isStopped = true;
        try {
            this.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.interrupt();
    }
}
