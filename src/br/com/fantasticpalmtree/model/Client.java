package br.com.fantasticpalmtree.model;

import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.persistence.BankAccountDao;

public class Client {
    static private long idCounter = 1;

    private long id = idCounter++;
    private String name;

    public Client(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static void startClients() {
        int customersAmount = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.CUSTOMERS_AMOUNT));
        int startingBalance = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.BANK_ACCOUNT_INITIAL_BALANCE));

        for (int i = 1; i <= customersAmount; i++) {
            Client client = new Client("Client - " + i);
            BankAccountDao.getInstance().save(new BankAccount(startingBalance, client));
        }
    }
}
