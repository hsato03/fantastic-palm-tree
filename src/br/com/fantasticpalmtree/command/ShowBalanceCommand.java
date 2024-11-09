package br.com.fantasticpalmtree.command;

import java.util.concurrent.CountDownLatch;

public class ShowBalanceCommand extends BaseCommand {
    private final CountDownLatch latch;

    public ShowBalanceCommand(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println("-----------------Show balance-----------------");
        bankAccountDao.findAll().forEach(System.out::println);
        System.out.println("----------------------------------------------");

        latch.countDown();
    }
}
