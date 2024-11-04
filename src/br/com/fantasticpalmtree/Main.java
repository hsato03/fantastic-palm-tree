package br.com.fantasticpalmtree;

import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.dto.DepositRequest;
import br.com.fantasticpalmtree.dto.TransferRequest;
import br.com.fantasticpalmtree.model.BankAccount;
import br.com.fantasticpalmtree.model.Client;
import br.com.fantasticpalmtree.model.Server;
import br.com.fantasticpalmtree.persistence.BankAccountDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    private static final double transactionMinValue = Double.parseDouble(ConfigLoader.getInstance().getProperty(PropertyConstants.FINANCIAL_TRANSACTION_MIN_VALUE));
    private static final double transactionMaxValue = Double.parseDouble(ConfigLoader.getInstance().getProperty(PropertyConstants.FINANCIAL_TRANSACTION_MAX_VALUE));
    private static final int customersAmount = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.CUSTOMERS_AMOUNT));
    private static final int startingBalance = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.BANK_ACCOUNT_INITIAL_BALANCE));

    public static void main(String[] args) {
        Server server = Server.getInstance();
        Thread serverThread = new Thread(server::start);

        serverThread.start();

        List<Client> clients = startClients();


        for (int i = 0; i < clients.size() * 100; i++) {
            Client client = clients.get(getRandomId());
            server.addRequest(new DepositRequest(client.getId(), getRandomValue()));
            server.addRequest(new TransferRequest(client.getId(), getRandomValue(), (client.getId() + 1) % clients.size()));
        }
    }

    public static List<Client> startClients() {
        ArrayList<Client> clients = new ArrayList<>();

        for (int i = 0; i < customersAmount; i++) {
            Client client = new Client("Client - " + i);
            clients.add(client);
            BankAccountDao.getInstance().save(new BankAccount(startingBalance, client));
        }

        return clients;
    }

    public static double getRandomValue() {
        return ThreadLocalRandom.current().nextDouble(transactionMinValue, transactionMaxValue);
    }

    public static int getRandomId() {
        return ThreadLocalRandom.current().nextInt(0, customersAmount);
    }
}
