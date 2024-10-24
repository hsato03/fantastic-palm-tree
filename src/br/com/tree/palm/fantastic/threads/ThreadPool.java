package br.com.tree.palm.fantastic.threads;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ThreadPool {
    private final Queue<Runnable> taskQueue;
    private final List<Consumer> consumers;
    private boolean isStopped;

    public ThreadPool(int numThreads) {
        taskQueue = new LinkedList<>();
        consumers = new ArrayList<>();
        isStopped = false;

        for (int i = 0; i < numThreads; i++) {
            consumers.add(new Consumer(taskQueue));
        }

        for (Consumer consumer : consumers) {
            consumer.start();
        }
    }

    public synchronized void submit(Runnable task) {
        if (isStopped) {
            throw new IllegalStateException("Thread Pool is stopped.");
        }

        taskQueue.add(task);
        notify();
    }

    public void shutdown() {
        isStopped = true;

        for (Consumer consumer : consumers) {
            consumer.doStop();
        }
    }
}
