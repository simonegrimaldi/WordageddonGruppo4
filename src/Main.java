import dao.connection.dbConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/LoginIn.fxml")); // o "/view/Home.fxml" se è in una sottocartella
            Scene scene = new Scene(root);
            primaryStage.setTitle("Wordageddon");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println("❌ Errore nel caricamento della view:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            Connection conn = dbConnection.getConnection();
            if (conn != null && !conn.isClosed()) {
                System.out.println("✅ Connessione al database riuscita!");
            } else {
                System.out.println("❌ Connessione fallita.");
            }
        } catch (Exception e) {
            System.out.println("❌ Errore durante la connessione al database:");
            e.printStackTrace();
        }
        launch(args); // richiama start()
    }
}