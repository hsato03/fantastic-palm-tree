package br.com.fantasticpalmtree.command;

public class ShowBalanceCommand extends BaseCommand {

    @Override
    public void run() {
        System.out.println("-----------------Show balance-----------------");
        bankAccountDao.findAll().forEach(System.out::println);
        System.out.println("----------------------------------------------");
    }
}
