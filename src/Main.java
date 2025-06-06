import controller.HomeController;
import dao.connection.DbConnection;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application {
    
    private static Scene scene;
      @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("/view/LogIn"), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        
    }

    private static Parent loadFXML(String fxml/*Object data*/) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxml + ".fxml"));
        
        /*if (fxml.equals("Home")) {
            fxmlLoader.setController(new HomeController((User) data));  // Casting il tipo corretto
        }*/ 
        
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        try {
            Connection conn = DbConnection.getConnection();
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