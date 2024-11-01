package br.com.fantasticpalmtree;

import br.com.fantasticpalmtree.command.CommandExecutor;
import br.com.fantasticpalmtree.command.executable.DepositCommand;
import br.com.fantasticpalmtree.command.executable.ShowBalanceCommand;
import br.com.fantasticpalmtree.command.executable.TransferCommand;
import br.com.fantasticpalmtree.config.ConfigLoader;
import br.com.fantasticpalmtree.config.PropertyConstants;

import java.util.Scanner;

public class ClientMain {
    public static void main(String[] args) {
        // TODO:
        //  - Criar mensageria cliente/servidor (socket?)
        //  - Implementar o metodo dos comandos
        //  - Permitir criar contas no lado do cliente
        //  - Criar servidor
        ConfigLoader loader = new ConfigLoader();
        System.out.println(loader.getProperty(PropertyConstants.THREAD_POOL_SIZE));
        System.out.println(loader.getProperty(PropertyConstants.NEW_REQUEST_INTERVAL));
        System.out.println(loader.getProperty(PropertyConstants.CUSTOMERS_AMOUNT));
        System.out.println(loader.getProperty(PropertyConstants.SERVICE_INTERVAL));

        CommandExecutor commandExecutor = new CommandExecutor();
        Scanner scanner = new Scanner(System.in);
        int chosenOption = 0;

        while (true) {
            showMenu();

            try {
                chosenOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }

            switch (chosenOption) {
                case 1 -> commandExecutor.executeCommand(new DepositCommand());
                case 2 -> commandExecutor.executeCommand(new TransferCommand());
                case 3 -> commandExecutor.executeCommand(new ShowBalanceCommand());
                case 4 -> {
                    System.out.println("Leaving...");
                    scanner.close();
                    System.exit(0);
                }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("\nENTER THE NUMBER OF THE DESIRED OPERATION:");
        System.out.println("[1] Deposit money to the account");
        System.out.println("[2] Transfer money to another account");
        System.out.println("[3] Show current account balance");
        System.out.println("[4] Exit");
    }
}