package br.com.fantasticpalmtree.command;

import br.com.fantasticpalmtree.model.BankAccount;

public class DepositCommand extends BaseCommand {

    @Override
    public void run() {
        BankAccount bankAccount = bankAccountDao.findById(getRandomId());
        double randomValue = getRandomValue();
        System.out.println(String.format("Depositando R$ %.2f", randomValue));
        bankAccount.deposit(randomValue);
        System.out.println(String.format("%.2f", bankAccount.getBalance()));
    }
}
