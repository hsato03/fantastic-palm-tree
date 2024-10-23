import br.com.tree.palm.fantastic.command.CommandExecutor;
import br.com.tree.palm.fantastic.command.DepositCommand;
import br.com.tree.palm.fantastic.command.ShowBalanceCommand;
import br.com.tree.palm.fantastic.command.TransferCommand;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutor();
        Scanner scanner = new Scanner(System.in);
        int choosenOption = 0;

        while (true) {
            showMenu();

            try {
                choosenOption = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException ignored) {
            }

            switch (choosenOption) {
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