package br.com.tree.palm.fantastic.threads;

import java.util.Queue;

public class Consumer extends Thread {
    private final Queue<Runnable> taskQueue;
    private boolean isStopped;

    public Consumer(Queue<Runnable> taskQueue) {
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

    public synchronized void doStop() {
        isStopped = true;
        this.interrupt();
    }
}
