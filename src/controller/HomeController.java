/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
 */
public class HomeController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Label title;
    @FXML
    private Button profileButton;
    @FXML
    private Button playButton;
    @FXML
    private VBox playContainer;
    @FXML
    private SplitMenuButton difficultyChooser;
    @FXML
    private Button startGame;
    @FXML
    private Label labelRules;
    @FXML
    private VBox profileContainer;
    @FXML
    private Label textArea;
    @FXML
    private Label ranking;
    @FXML
    private Label ScoreLabel;

    private String username;
    private ChangeView controller;
    private boolean isVisibleProfile = false;
    private boolean isVisiblePlay = false;

    public void setChangeViewController(ChangeView controller, String username) {
        this.controller = controller;
        this.username = username;
        title.textProperty().setValue("Hello " + username + " !");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profileContainer.managedProperty().bind(profileContainer.visibleProperty());
        playContainer.managedProperty().bind(playContainer.visibleProperty());
        
        MenuItem easy = new MenuItem("Facile");
        MenuItem medium = new MenuItem("Media");
        MenuItem hard = new MenuItem("Difficile");
        easy.setOnAction(e -> difficultyChooser.setText("Facile"));
        medium.setOnAction(e -> difficultyChooser.setText("Media"));
        hard.setOnAction(e -> difficultyChooser.setText("Difficile"));
        difficultyChooser.getItems().addAll(easy, medium, hard);
    }

    @FXML
    private void logoutButtonClick(ActionEvent event) {
        controller.goLogIn();
    }

    @FXML
    private void profileButtonClick(ActionEvent event) {
        if (!profileContainer.isVisible()) {
            profileContainer.setVisible(true);
            playContainer.setVisible(false);
        } else {
            profileContainer.setVisible(false);
        }
    }

    @FXML
    private void playButtonClick(ActionEvent event) {
        if (!playContainer.isVisible()) {
            playContainer.setVisible(true);
            profileContainer.setVisible(false);
        } else {
            playContainer.setVisible(false);
        }
    }

    @FXML
    private void startGameClick(ActionEvent event) {
    }

    @FXML
    private void difficultyChooser(ActionEvent event) {

    }

}
