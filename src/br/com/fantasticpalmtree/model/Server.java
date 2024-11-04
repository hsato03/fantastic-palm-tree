package br.com.fantasticpalmtree.model;

import br.com.fantasticpalmtree.command.DepositCommand;
import br.com.fantasticpalmtree.command.ShowBalanceCommand;
import br.com.fantasticpalmtree.command.TransferCommand;
import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.dto.BaseRequest;
import br.com.fantasticpalmtree.dto.DepositRequest;
import br.com.fantasticpalmtree.dto.TransferRequest;
import br.com.fantasticpalmtree.threads.ThreadPool;

import java.util.ArrayList;
import java.util.List;

public final class Server {
    private static Server instance;
    private final TransferCommand transferCommand = new TransferCommand();
    private final ShowBalanceCommand showBalanceCommand = new ShowBalanceCommand();
    private final DepositCommand depositCommand = new DepositCommand();
    private final ThreadPool threadPool;
    private final List<BaseRequest> requests = new ArrayList<>();

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

    public void addRequest(BaseRequest request) {
        requests.add(request);
    }

    public void start() {
        while(true){
            synchronized (requests) {
                if (!requests.isEmpty()) {
                    BaseRequest request = requests.remove(0);

                    if (request instanceof TransferRequest transferRequest) transferBalance(
                            transferRequest.getOrigin(),
                            transferRequest.getTarget(),
                            transferRequest.getValue());

                    else if (request instanceof DepositRequest depositRequest)
                        deposit(depositRequest.getAccount(), depositRequest.getValue());
                }
            }
            try {
                Thread.sleep(100, 0);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void transferBalance(long from, long to, double value) {
        Runnable function = () -> {
            transferCommand.run(from, to, value);
            incrementOperationCount();
        };

        threadPool.submit(function);
    }

    private void deposit(long id, double value) {
        Runnable function = () -> {
            depositCommand.run(id, value);
            incrementOperationCount();
        };

        threadPool.submit(function);
    }

    public void showBalance() {
        threadPool.submitPriorityTask(showBalanceCommand);
    }

    public void shutdown() {
        threadPool.shutdown();
    }

    private volatile int operationCount = 0;

    private synchronized void incrementOperationCount() {
        operationCount++;

        if (operationCount >= 10) {
            operationCount = 0;
            showBalance();
        }
    }
}
