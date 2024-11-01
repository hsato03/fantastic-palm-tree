package br.com.fantasticpalmtree.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool {
    private final Queue<Runnable> taskQueue;
    private final List<WorkerThread> workerThreads;
    private volatile boolean isStopped;

    public ThreadPool(int numThreads) {
        taskQueue = new LinkedList<>();
        workerThreads = new ArrayList<>();
        isStopped = false;

        for (int i = 0; i < numThreads; i++) {
            workerThreads.add(new WorkerThread(taskQueue));
        }

        for (WorkerThread workerThread : workerThreads) {
            workerThread.start();
        }
    }

    public void submit(Runnable task) {
        if (isStopped) {
            throw new IllegalStateException("Thread Pool is stopped.");
        }

        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    public void shutdown() {
        isStopped = true;

        for (WorkerThread workerThread : workerThreads) {
            workerThread.setStopped(isStopped);
            try {
                workerThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            workerThread.doStop();
        }
    }
}
