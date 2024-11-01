package br.com.fantasticpalmtree;

import br.com.fantasticpalmtree.command.DepositCommand;
import br.com.fantasticpalmtree.command.ShowBalanceCommand;
import br.com.fantasticpalmtree.command.TransferCommand;
import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;
import br.com.fantasticpalmtree.model.BankAccount;
import br.com.fantasticpalmtree.persistence.BankAccountDao;
import br.com.fantasticpalmtree.threads.ThreadPool;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // TODO:
        //  - Criar mensageria cliente/servidor (socket?)
        //  - Implementar o metodo dos comandos
        //  - Criar servidor
        int threadPoolSize = Integer.parseInt(ConfigLoader.getInstance().getProperty(PropertyConstants.THREAD_POOL_SIZE));

        BankAccountDao bankAccountDao = BankAccountDao.getInstance();
        BankAccount bankAccount = new BankAccount(1000, "Teste");
        bankAccountDao.save(bankAccount);

        ThreadPool pool = new ThreadPool(threadPoolSize);

        pool.submit(new DepositCommand());
        pool.submit(new DepositCommand());
        pool.submit(new DepositCommand());
        pool.submit(new DepositCommand());

        pool.shutdown();

//        Scanner scanner = new Scanner(System.in);
//        int chosenOption = 0;
//
//        while (true) {
//            try {
//                chosenOption = Integer.parseInt(scanner.nextLine());
//            } catch (NumberFormatException ignored) {
//            }
//
//            switch (chosenOption) {
//                case 1 -> pool.submit(new DepositCommand());
//                case 2 -> pool.submit(new TransferCommand());
//                case 3 -> pool.submit(new ShowBalanceCommand());
//                case 4 -> {
//                    System.out.println("Leaving...");
//                    scanner.close();
//                    System.exit(0);
//                }
//                default -> System.out.println("Invalid option.");
//            }
//        }
    }
}