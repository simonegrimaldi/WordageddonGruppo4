package controller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

/**
 * @class EntryController
 * @brief Controller associato alla schermata di ingresso (splash screen)
 * dell'applicazione.
 *
 * La classe gestisce l'animazione della schermata di ingresso con effetti di
 * fade-in e scaling. Al termine dell'animazione, la schermata di ingresso viene
 * nascosta e l'utente viene indirizzato alla schermata di login.
 *
 * Utilizza la libreria JavaFX per la gestione dell'interfaccia utente e delle
 * animazioni.
 */
public class EntryController {

    @FXML
    private AnchorPane splashScreenPane;
    @FXML
    private ImageView splashImage;
    private ChangeView controller;

    /**
     * @brief Imposta il controller per la navigazione tra le schermate.
     *
     * @param controller Il controller per gestire il cambio di schermata.
     */
    public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

    /**
     * @brief Metodo che inizializza la splash screen con animazioni di fade e
     * scaling. Viene eseguito al caricamento della schermata.
     */
    public void initialize() {

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(5), splashImage);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ScaleTransition scaleUp = new ScaleTransition(Duration.seconds(5), splashImage);
        scaleUp.setFromX(1);
        scaleUp.setToX(1.5);
        scaleUp.setFromY(1);
        scaleUp.setToY(1.5);

        fadeIn.setOnFinished(e -> {
            Platform.runLater(() -> {
                showMainAppScreen();
            });
        });

        fadeIn.play();
        scaleUp.play();
    }

    /**
     * @brief Nasconde la splash screen e cambia la vista a quella di login.
     */
    private void showMainAppScreen() {

        splashScreenPane.setVisible(false);

        controller.goLogIn();
    }
}
