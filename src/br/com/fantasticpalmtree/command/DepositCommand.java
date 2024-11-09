package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.model.BankAccount;
import br.com.fantasticpalmtree.model.Server;

public class DepositCommand extends BaseCommand {

    @Override
    public void run() {
        Server.getInstance().incrementOperationCount();
        BankAccount bankAccount = bankAccountDao.findById(getRandomId());

        double value = getRandomValue();
        bankAccount.deposit(value);

        System.out.println(String.format("[ DEPOSIT ] - Account: %s Value: %.2f", bankAccount, value));
    }
}
