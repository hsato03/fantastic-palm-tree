package main.br.com.fantasticpalmtree.command;

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
