package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.model.BankAccount;

public class DepositCommand extends BaseCommand {

    public void run(long id, double value) {
        BankAccount bankAccount = bankAccountDao.findById(id);
        bankAccount.deposit(value);

        System.out.println("[ DEPOSIT ] - Account: " + bankAccount + " Value: " + value);
    }
}
