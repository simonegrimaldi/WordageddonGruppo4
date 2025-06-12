package main;

import controller.ChangeViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ChangeViewController controller = new ChangeViewController(primaryStage);

        controller.goEntry();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
