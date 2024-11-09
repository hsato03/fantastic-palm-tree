package br.com.fantasticpalmtree;

import br.com.fantasticpalmtree.command.DepositCommand;
import br.com.fantasticpalmtree.command.TransferCommand;
import br.com.fantasticpalmtree.model.Client;
import br.com.fantasticpalmtree.model.Server;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        Server server = Server.getInstance();
        Client.startClients();

        while (true) {
            if (ThreadLocalRandom.current().nextInt() % 2 == 0) {
                server.addRequest(new DepositCommand());
            } else {
                server.addRequest(new TransferCommand());
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
