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

public class Main {
    public static void main(String[] args) {
        // TODO:
        //  - Criar mensageria cliente/servidor (socket?)
        //  - Implementar o metodo dos comandos - Ok
        //  - Criar servidor - Ok

        Server server = Server.getInstance();
        Thread serverThread = new Thread(server::start);

        serverThread.start();

        List<Client> clients = startClients();


        for (int i = 0; i < clients.size() * 100; i++) {
            Client client = clients.get(i % clients.size());
            server.addRequest(new DepositRequest(client.getId(), 10*i));
            server.addRequest(new TransferRequest(client.getId(), 10*i, (client.getId() + 1) % clients.size()));
        }
    }

    public static List<Client> startClients() {
        int customersAmount = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.CUSTOMERS_AMOUNT));
        int startingBalance = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.BANK_ACCOUNT_INITIAL_BALANCE));

        ArrayList<Client> clients = new ArrayList<>();

        for (int i = 0; i < customersAmount; i++) {
            Client client = new Client("Client - " + i);
            clients.add(client);
            BankAccountDao.getInstance().save(new BankAccount(startingBalance, client));
        }

        return clients;
    }
}
