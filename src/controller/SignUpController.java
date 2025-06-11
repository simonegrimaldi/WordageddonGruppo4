package controller;

import util.AlertManager;
import dao.interfaces.DaoUser;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @class SignUpController
 *
 * @brief Controller per la schermata di SignUp. Gestisce la registrazione di un
 * utente che non è registrato. Include azione come la compilazione di tutti i
 * campi e l'interazione con il database per controllare la correttazza dei dati
 * inseriti e salvarli.
 */
public class SignUpController implements Initializable {

    @FXML
    private TextField passwordTextField;  // Per la password visibile in chiaro
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button backButton;
    @FXML
    private Button SignUpButton;
    
    @FXML
    private ImageView simboloMostraPassword;

    private ChangeView controller;
    private AlertManager alert;
    private DaoUser daoUser;

    /**
     * @brief Imposta il controller per la navigazione tra le schermate e il
     * DaoUser per la verifica delle credenziali e la gestione degli utenti
     *
     * @param controller
     * @param daoUser
     */
    public void setChangeViewController(ChangeView controller, DaoUser daoUser) {
        this.daoUser = daoUser;
        this.controller = controller;
    }

    /**
     * @brief Initialize the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert = new AlertManager();
       
    }

    /**
     * @brief Gestisce la pressione del bottone che ci riporta alla schermata di
     * login
     *
     * Una volta che il bottone viene premuto, l'"utente" viene reindirizzato
     * alla schermata di login.
     *
     * @param event
     */
    @FXML
    private void backButtonClick(ActionEvent event) {
        controller.goLogIn();
    }

    /**
     * @brief Metodo che gestisce il click sul pulsante di SignUp
     *
     * Verifica anzi tutto che sia i campi username e password siano stati
     * compilati, nel caso in cui i campi siano vuoti verrà mostrato un
     * messaggio di errore. Se l'utente ha compilato tutti i campi correttamente
     * e non è già presente nel databese, i dati verranno salvati e l'utente
     * verrà reindirizzato alla schermata Home.
     *
     * Se i dati inseriti sono già presenti nel database verrà mostrato un
     * messaggio di errore.
     *
     * @param event
     */
    @FXML
    private void SignUpButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            ButtonType response = alert.showAlert("Errore", "Tutti i campi sono obbligatori", "ERROR");
        } else {
            if (daoUser.registration(username, password)) {
                controller.goHome(username);
            } else {
                ButtonType response = alert.showAlert("Errore", "L'username già esiste", "ERROR");
            }
        }
    }
    public void viewPassword() {
    if (passwordField.isVisible()) {
        // Se la password è nascosta, mostriamola in chiaro
        passwordField.setVisible(false);
        passwordTextField.setVisible(true);
        passwordTextField.setText(passwordField.getText());  // Copia la password nel TextField
        
        // Cambia l'icona in "occhio aperto"
simboloMostraPassword.setImage(new Image(getClass().getResource("/utilities/eye.png").toExternalForm()));

    } else {
        // Se la password è visibile, nascondiamola
        passwordTextField.setVisible(false);
        passwordField.setVisible(true);
        passwordField.setText(passwordTextField.getText());  // Copia la password dal TextField nel PasswordField
        
        // Cambia l'icona in "occhio chiuso"
        simboloMostraPassword.setImage(new Image(getClass().getResource("/utilities/hidden.png").toExternalForm()));
    }
}


}
