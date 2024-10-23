package br.com.tree.palm.fantastic.command;

public class CommandExecutor {
    public void executeCommand(Command command) {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        command.execute();
    }
}
