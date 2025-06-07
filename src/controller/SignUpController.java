/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import util.AlertManager;
import dao.implementation.DaoUserImpl;
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
    private DaoUserImpl dao;
    /**
     * Initializes the controller class.
     */
    ChangeView controller;
    AlertManager alert;
      
    public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dao=new DaoUserImpl();
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
            alert.showAlert("Errore", "Tutti i campi sono obbligatori");
        }else{
        if(dao.registration(username, password)){
            controller.goHome(username);
        }else{
            alert.showAlert("Errore", "L'username già esiste");
        }
        }
    }
    
}
