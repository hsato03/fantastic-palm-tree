package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.model.BankAccount;
import br.com.fantasticpalmtree.model.Server;

public class TransferCommand extends BaseCommand {

    @Override
    public void run() {
        if (customersAmount < 2) {
            System.out.println("Cannot perform the transfer operation as it is necessary to have at least 2 accounts available.");
            return;
        }

        Server.getInstance().incrementOperationCount();
        BankAccount fromAccount = bankAccountDao.findById(getRandomId());
        BankAccount toAccount = bankAccountDao.findById(getRandomId());

        if (fromAccount.getId() == toAccount.getId()) {
            toAccount = bankAccountDao.findById(((toAccount.getId() + 1) % customersAmount) + 1);
        }

        double value = getRandomValue();
        fromAccount.withdraw(value);
        toAccount.deposit(value);

        System.out.println(String.format("[ TRANSFER ] - From: %s To: %s Value: %.2f", fromAccount,  toAccount, value));
    }
}
