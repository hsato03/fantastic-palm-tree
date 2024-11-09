package br.com.fantasticpalmtree.threads;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final Deque<Runnable> taskQueue;
    private final List<WorkerThread> workerThreads;
    private volatile boolean isStopped;

    public ThreadPool(int numThreads) {
        this.taskQueue = new ArrayDeque<>();
        workerThreads = new LinkedList<>();
        isStopped = false;

        for (int i = 0; i < numThreads; i++) {
            workerThreads.add(new WorkerThread(this.taskQueue));
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
            taskQueue.addFirst(task);
            taskQueue.notifyAll();
        }

        // Replace a Thread to avoid deadlock when all the other threads are blocked.
        WorkerThread workerThread = new WorkerThread(taskQueue);
        workerThreads.add(workerThread);
        workerThreads.remove(getIdleThread());
        workerThread.start();
    }

    public WorkerThread getIdleThread() {
        return workerThreads.stream().filter(workerThread -> workerThread.getState() != Thread.State.RUNNABLE).findFirst().get();
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
