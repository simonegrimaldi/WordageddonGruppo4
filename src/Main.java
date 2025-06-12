import controller.ChangeViewController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Crea il controller e passa il primaryStage
        ChangeViewController controller = new ChangeViewController(primaryStage);
        
        // Mostra la schermata di login
        controller.goEntry();
    }

    public static void main(String[] args) {
        launch(args);  // Avvia l'applicazione JavaFX
    }
}