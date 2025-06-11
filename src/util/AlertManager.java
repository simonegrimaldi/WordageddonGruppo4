/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * Gestione degli alert in modo centralizzato.
 */
public class AlertManager {

    /**
     * Mostra un alert di tipo specificato.
     * 
     * @param title Titolo dell'alert
     * @param message Messaggio dell'alert
     * @param type Tipo dell'alert (es. "CONFIRMATION", "ERROR", ecc.)
     * @return La risposta dell'utente sotto forma di ButtonType, se il tipo è una conferma
     */
    public ButtonType showAlert(String title, String message, String type) {
        // Crea l'alert con il tipo specificato
        Alert alert = new Alert(Alert.AlertType.valueOf(type.toUpperCase()));

        alert.setTitle(title);  
        alert.setHeaderText(null);  
        alert.setContentText(message);
        
        if (type.equalsIgnoreCase("CONFIRMATION")) {
            // Solo se è di tipo CONFIRMATION, mostriamo e aspettiamo una risposta
            return alert.showAndWait().orElse(ButtonType.CANCEL);  // Restituisce ButtonType.OK o ButtonType.CANCEL
        } else {
            // Per tipi che non richiedono risposta, mostriamo e chiudiamo senza restituire nulla
            alert.showAndWait();
            return null;
        }
    }
}

