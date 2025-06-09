/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import util.AlertManager;
import dao.implementation.DaoUserImpl;
import dao.interfaces.DaoUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Button backButton;
    @FXML
    private Button SignUpButton;
    
    private ChangeView controller;
    private AlertManager alert;
    private DaoUser daoUser;
    
    /**
     * Initializes the controller class.
     */
      
    public void setChangeViewController(ChangeView controller,DaoUser daoUser) {
        this.daoUser = daoUser;
        this.controller = controller;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert=new AlertManager();
    }    

    @FXML
    private void backButtonClick(ActionEvent event) {
        controller.goLogIn();
    }

    @FXML
    private void SignUpButtonClick(ActionEvent event) {
        String username=usernameField.getText();
        String password=passwordField.getText();
        if(username.isEmpty() || password.isEmpty()){
            alert.showAlert("Errore", "Tutti i campi sono obbligatori","ERROR");
        }else{
        if(daoUser.registration(username, password)){
            controller.goHome(username);
        }else{
            alert.showAlert("Errore", "L'username già esiste","ERROR");
        }
        }
    }
    
}
