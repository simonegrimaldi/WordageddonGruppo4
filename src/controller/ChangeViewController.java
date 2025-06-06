package controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;

public class ChangeViewController implements ChangeView {

    private Stage primaryStage;

    public ChangeViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void goHome(String username) {
        
        try {
            show("Home", username);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goSignUp() {
        try {
            show("SignUp", null);  // Torna alla schermata di login
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show(String fxml, String username) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml + ".fxml"));
        Parent root = loader.load();

        // Imposta il controller per la vista
        if ("Home".equals(fxml)) {
            HomeController homeController = loader.getController();
            homeController.setUsername(username);
        }
        
        if ("LogIn".equals(fxml)) {
            // Il controller viene caricato automaticamente da FXMLLoader
            LogInController loginController = loader.getController();
            loginController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata di login
        }

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}