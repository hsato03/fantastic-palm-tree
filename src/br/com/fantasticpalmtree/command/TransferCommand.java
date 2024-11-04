package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.model.BankAccount;

public class TransferCommand extends BaseCommand {

    public void run(long from, long to, double value) {
        BankAccount fromAccount = bankAccountDao.findById(from);
        BankAccount toAccount = bankAccountDao.findById(to);

        if (fromAccount == null || toAccount == null) {
            System.out.println("Account not found");
            return;
        }

        fromAccount.withdraw(value);
        toAccount.deposit(value);

        System.out.println(String.format("[ TRANSFER ] - From: %s To: %s Value: %.2f", fromAccount, toAccount, value));
    }
}
