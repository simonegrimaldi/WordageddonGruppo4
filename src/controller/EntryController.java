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
    private AnchorPane splashScreenPane;
    @FXML
    private ImageView splashImage; 
    private ChangeView controller;

    public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

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

    private void showMainAppScreen() {
        
        splashScreenPane.setVisible(false);

        
        controller.goLogIn();
    }
}
