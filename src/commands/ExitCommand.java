package commands;

import application.CollectionManager;

public class ExitCommand {

    private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute() {
        System.out.println("Program will be finished now.");
        System.exit(0);
        return "Program will be finished now.";
    }

}
