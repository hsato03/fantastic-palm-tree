package br.com.fantasticpalmtree.model;

import br.com.fantasticpalmtree.command.BaseCommand;
import br.com.fantasticpalmtree.command.ShowBalanceCommand;
import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.threads.ThreadPool;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public final class Server {
    private static Server instance;
    private final ThreadPool threadPool;
    private final Queue<Runnable> requests = new LinkedList<>();
    private volatile int operationCount = -1;

    private Server(int numThreads) {
        threadPool = new ThreadPool(numThreads);
    }

    public static synchronized Server getInstance() {
        if (instance == null) {
            int threadPoolSize = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.THREAD_POOL_SIZE));
            instance = new Server(threadPoolSize);
        }
        return instance;
    }

    public void addRequest(BaseCommand request) {
        threadPool.submit(request);
    }

    public void showBalance() {
        CountDownLatch latch = new CountDownLatch(1);
        threadPool.submitPriorityTask(new ShowBalanceCommand(latch));

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    public synchronized void incrementOperationCount() {
        operationCount++;

        if (operationCount >= 10) {
            operationCount = 0;

            showBalance();
        }
    }

}
