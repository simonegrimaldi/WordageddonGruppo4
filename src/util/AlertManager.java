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
    Alert alert = new Alert(Alert.AlertType.ERROR);
    
    public void showAlert(String title, String message) {
    alert.setTitle(title);  // Imposta il titolo dell'alert
    alert.setHeaderText(null);  // Non vogliamo un'intestazione
    alert.setContentText(message);  // Imposta il messaggio dell'alert
    alert.showAndWait();  // Mostra l'alert e aspetta che l'utente lo chiuda
}
}
