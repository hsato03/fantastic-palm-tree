package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.persistence.BankAccountDao;

import java.util.concurrent.ThreadLocalRandom;

public abstract class BaseCommand {
    protected ConfigLoader configLoader = ConfigLoader.getInstance();
    protected BankAccountDao bankAccountDao = BankAccountDao.getInstance();

    private final int customersAmount = Integer.parseInt(configLoader.getProperty(PropertyConstants.CUSTOMERS_AMOUNT));
    private final double transactionMinValue = Double.parseDouble(configLoader.getProperty(PropertyConstants.FINANCIAL_TRANSACTION_MIN_VALUE));
    private final double transactionMaxValue = Double.parseDouble(configLoader.getProperty(PropertyConstants.FINANCIAL_TRANSACTION_MAX_VALUE));

    public int getRandomId() {
        return ThreadLocalRandom.current().nextInt(1, customersAmount + 1);
    }

    public double getRandomValue() {
        return ThreadLocalRandom.current().nextDouble(transactionMinValue, transactionMaxValue);
    }
}
