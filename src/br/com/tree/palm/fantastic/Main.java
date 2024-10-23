package br.com.tree.palm.fantastic;

import br.com.tree.palm.fantastic.command.CommandExecutor;
import br.com.tree.palm.fantastic.command.executable.DepositCommand;
import br.com.tree.palm.fantastic.command.executable.ShowBalanceCommand;
import br.com.tree.palm.fantastic.command.executable.TransferCommand;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
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