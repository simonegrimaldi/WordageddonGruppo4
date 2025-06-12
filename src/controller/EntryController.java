package controller;

import dao.interfaces.DaoUser;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class EntryController {

    @FXML
    private AnchorPane splashScreenPane; // Il pannello dove avviene l'animazione
    @FXML
    private ImageView splashImage; // Il logo che vuoi animare
    private ChangeView controller;

    public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

    public void initialize() {
        // FadeTransition: animazione di fade per rendere l'immagine visibile
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), splashImage);
        fadeIn.setFromValue(0); // Comincia da invisibile
        fadeIn.setToValue(1);   // Arriva a visibile

        // ScaleTransition: animazione di ingrandimento per l'immagine
        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(5), splashImage);
        scaleUp.setFromX(1);  // Inizia con metà dimensione
        scaleUp.setToX(1.5);      // Arriva alla dimensione originale sull'asse X
        scaleUp.setFromY(1);  // Inizia con metà dimensione
        scaleUp.setToY(1.5);      // Arriva alla dimensione originale sull'asse Y

        // Dopo che l'animazione di fade e ingrandimento è terminata, passa alla schermata principale
        fadeIn.setOnFinished(e -> {
            Platform.runLater(() -> {
                showMainAppScreen();
            });
        });

        fadeIn.play(); // Avvia l'animazione di fade
        scaleUp.play(); // Avvia l'animazione di ingrandimento
    }

    private void showMainAppScreen() {
        // Nasconde la schermata di splash e mostra la schermata principale
        splashScreenPane.setVisible(false);

        // Carica la schermata principale dell'app (modifica questo metodo secondo la tua logica)
        controller.goLogIn();
    }
}
