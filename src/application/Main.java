package application;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(() -> {
            DatabaseManager databaseManager = new DatabaseManager();
            CollectionManager collectionManager = new CollectionManager(databaseManager);
            CommandReader commandReader = new CommandReader(collectionManager, new DatabaseManager());
            commandReader.run();
        });
        executorService.shutdown();
    }


}
