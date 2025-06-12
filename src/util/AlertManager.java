package util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 * @class AlertManager
 * @brief Classe che gestisce gli alert in modo centralizzato all'interno di
 * tutta l'applicazione
 *
 * La classe fornisce un metodo per mostrare alert di tipo diverso (di errore,
 * di conferma) in modo centralizzato. Gestisce il titolo, il contenuto e la
 * tipologia di alert.
 *
 */
public class AlertManager {

    /**
     * @brief Mostra un alert di tipo specificato
     *
     * Il metodo crea un alert con il tipo e il titolo specificato. Nel caso in
     * cui l'alert sia di tipo 'CONFIRMATION', l'utente dovrà rispondere tramite
     * un pulsante ('ok' o 'confirm'); tutti gli altri tipi di alert vengono
     * mostrati senza aspettarsi una risposta
     *
     * @param title Titolo dell'alert
     * @param message Messaggio dell'alert
     * @param type Tipo dell'alert (es. "CONFIRMATION", "ERROR", ecc.)
     * @return La risposta dell'utente sotto forma di ButtonType, se il tipo
     * 'CONFIRMATION'
     *
     * Restituisce {@link ButtonType#CANCEL} se l'utente non seleziona nulla. Se
     * il tipo non è "CONFIRMATION", non restituisce nulla.
     *
     * @pre {@code type} deve essere un valore valido riconosciuto dalla classe
     * {@link Alert.AlertType}.
     * @post Mostra un alert all'utente e restituisce la risposta dell'utente
     * (solo per gli alert di tipo "CONFIRMATION").
     *
     */
    public ButtonType showAlert(String title, String message, String type) {
        // Creo l'alert con il tipo specificato
        Alert alert = new Alert(Alert.AlertType.valueOf(type.toUpperCase()));

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (type.equalsIgnoreCase("CONFIRMATION")) {
            return alert.showAndWait().orElse(ButtonType.CANCEL);
        } else {
            alert.showAndWait();
            return null;
        }
    }
}
