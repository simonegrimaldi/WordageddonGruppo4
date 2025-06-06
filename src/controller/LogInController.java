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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
 */
public class LogInController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button LoginInButton;
    
    AlertManager alertManager=new AlertManager();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SignUpButtonClick(ActionEvent event) {
    }

    @FXML
    private void LoginInButtonClick(ActionEvent event) {
        String username=usernameField.getText();
        String password=passwordField.getText();
        
        if(username.isEmpty() || password.isEmpty()){
            alertManager.showAlert("ERRORE", "entrambi i campi Username e Password devono essere compilati");
            return;
        }
        
        
    }
}
