package br.com.fantasticpalmtree.threads;

import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;

import java.util.Queue;

public class WorkerThread extends Thread {
    private final Queue<Runnable> taskQueue;
    private volatile boolean isStopped;

    private final int serviceInterval = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.SERVICE_INTERVAL)) * 1000;

    public WorkerThread(Queue<Runnable> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void run() {
        while (!isStopped) {
            Runnable task;

            synchronized (taskQueue) {
                while (taskQueue.isEmpty() && !isStopped) {
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
                    Thread.sleep(serviceInterval);
                    task.run();
                } catch (RuntimeException | InterruptedException e) {
                    System.out.println("Thread execution error: " + e.getMessage());
                }
            }
        }
    }

    public void doStop() {
        isStopped = true;
        this.interrupt();
    }

    public synchronized void setStopped(boolean stopped) {
        isStopped = stopped;
    }
}
