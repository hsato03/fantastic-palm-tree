package br.com.fantasticpalmtree.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final LinkedList<Runnable> taskQueue;
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

    public void submitPriorityTask(Runnable task) {
        if (isStopped) {
            throw new IllegalStateException("Thread Pool is stopped.");
        }

        synchronized (taskQueue) {
            stopThreads();

            while (true) {
                if (allThreadsAreBlocked()) {
                    taskQueue.add(0,task);
                    workerThreads.get(0).setStopped(false);
                    taskQueue.notify();

                    break;
                }
            }
            startThreads();
        }
    }

    private boolean allThreadsAreBlocked() {
        return workerThreads.stream()
                .filter(wT -> wT.getId() != Thread.currentThread().getId())
                .allMatch(wT -> wT.getState() == Thread.State.BLOCKED);
    }

    private void stopThreads() {
        for (WorkerThread workerThread : workerThreads) {
            workerThread.setStopped(true);
        }
    }

    private void startThreads() {
        for (WorkerThread workerThread : workerThreads) {
            workerThread.setStopped(false);
        }
    }

    public void shutdown() {
        isStopped = true;

        for (WorkerThread workerThread : workerThreads) {
            workerThread.setStopped(isStopped);

            synchronized (taskQueue) {
                taskQueue.notifyAll();
            }

            try {
                workerThread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            workerThread.doStop();
        }
    }
}
