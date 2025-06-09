/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;

/**
 *
 * @author User
 */
public class AlertManager {

    public void showAlert(String title, String message, String type) {
        Alert alert = new Alert(Alert.AlertType.valueOf(type.toUpperCase()));

        alert.setTitle(title);  
        alert.setHeaderText(null);  
        alert.setContentText(message);  
        alert.showAndWait();  
    }
}
