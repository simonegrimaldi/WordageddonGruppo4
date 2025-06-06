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

    @Override
    public void goLogIn() {
        try {
            show("LogIn", null);  // Torna alla schermata di login
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goAdminPanel(String superUsername) {
        try {
            show("AdminPanel", superUsername);  // Torna alla schermata di login
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goReading(String username) {
        try {
            show("Reading", username);  // Torna alla schermata di login
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goQuestion(String username) {
        try {
            show("Question", username);  // Torna alla schermata di login
        } catch (IOException e) {
            e.printStackTrace();
        }   
    }

    public void show(String fxml, String username) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml + ".fxml"));
    Parent root = loader.load();

    // Imposta il controller per la vista in base alla schermata
    switch (fxml) {
        case "Home":
            HomeController homeController = loader.getController();
            homeController.setUsername(username);
            break;

        case "LogIn":
            LogInController loginController = loader.getController();
            loginController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata di login
            break;

        case "SignUp":
            SignUpController signUpController = loader.getController();
            signUpController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata di registrazione
            break;
           
        case "AdminPanel":
            AdminPanelController adminPanelController = loader.getController();
            adminPanelController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata di admin
            break;

        case "Reading":
            ReadingController readingController = loader.getController();
            readingController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata di lettura
            break;

        case "Question":
            QuestionController questionController = loader.getController();
            questionController.setChangeViewController(this);  // Passa il controller ChangeView alla schermata delle domande
            break;

        default:
            // Caso di default se la schermata non è riconosciuta
            System.out.println("Unknown FXML: " + fxml);
            break;
    }

    // Crea e imposta la scena
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
}
}
