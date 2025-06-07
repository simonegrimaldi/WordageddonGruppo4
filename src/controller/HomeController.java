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
    private SplitMenuButton difficutChooser;
    @FXML
    private Button startGame;
    @FXML
    private Label labelRules;
    @FXML
    private StackPane profileContainer;
    @FXML
    private Button closeButton;
    @FXML
    private Label textArea;

    private String username;
    private ChangeView controller;
    private boolean isVisibleProfile = false;
    private boolean isVisiblePlay = false;

    public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        profileContainer.managedProperty().bind(profileContainer.visibleProperty());
        playContainer.managedProperty().bind(playContainer.visibleProperty());
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
    private void difficutChooserClick(ActionEvent event) {
    }

    @FXML
    private void startGameClick(ActionEvent event) {
    }

    @FXML
    private void handleCloseClick(ActionEvent event) {
    }

    public void setUsername(String username) {

    }
}
