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
 * @class LogInController
 *
 * @brief Controller per la schermata di login. Questa classe permette di
 * gestire l'interfaccia di login dell'utente, utilizzando un controllo delle
 * credenziali che sfrutta la classe `DaoUserImpl` e gestisce la navigazione tra
 * le schermate (Home, SignUp, etc.).
 *
 * Questo controller interagisce con la classe DaoUserImpl, al fine di
 * verificare la correttezza delle credenziali inserite e indirizzare gli
 * "utenti" alle schermate appropriate, che possono essere Home oppure
 * AdminPanel a seconda delle credenziali inserite.
 *
 * Gestisce il caso in cui un utente non sia registrato, reindirizzandolo alla
 * schermata atta alla registrazione.
 *
 * Vengono ovviamente gestiti i casi in cui le credenziali inserite non siano
 * corrette o uno dei campi non sia stato compilato.
 *
 */
public class LogInController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button SignUpButton;
    @FXML
    private Button LoginInButton;
    @FXML
    private TextField passwordTextField;  // Per la password visibile in chiaro
    @FXML
    private ImageView simboloMostraPassword;

    private AlertManager alertManager = new AlertManager();
    private ChangeView controller;
    private DaoUser daoUser;

    /**
     * @brief Imposta il controller per la navigazione tra le schermate e il
     * DaoUser per la verifica delle credenziali e la gestione degli utenti
     *
     * @param controller
     * @param daoUser
     */
    public void setChangeViewController(ChangeView controller, DaoUser daoUser) {
        this.controller = controller;
        this.daoUser = daoUser;
    }

    /**
     * @brief Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alertManager = new AlertManager();
         // Gestiamo l'evento del clic sull'icona dell'occhio
    simboloMostraPassword.setOnMouseClicked(event -> viewPassword());
    }

    /**
     * @brief Metodo che gestisce il click sul button di signUp.
     *
     * In qualsiasi momento il bottone viene premuto la vista cambia e si passa
     * alla schermata di registrazione.
     *
     * @param event
     */
    @FXML
    private void SignUpButtonClick(ActionEvent event) {
        controller.goSignUp();
    }

    /**
     * @brief Metodo che gestisce il click sul button di Login.
     *
     * Nel momento in cui sono stati inseriti username e password, verrà
     * effettuato un controllo per verificare l'effettiva corrispondenza con i
     * dati presenti nel database, in caso di esito positivo, l'User verrà
     * reindirizzato alla schermata Home; mentre per quanto riguarda l'Admin
     * quest'ultimo verrà reindirizzato all' AdminPanel.
     *
     * Quando uno dei campi username o password viene lasciato vuoto, allora
     * comparirà a video un messaggio di errore. Stessa cosa che succede nel
     * caso in cui le credenziali inserite non siano corrette.
     *
     * @param event
     */
    @FXML
    private void LoginInButtonClick(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            ButtonType response = alertManager.showAlert("ERRORE", "entrambi i campi Username e Password devono essere compilati", "error");
            return;
        }
        String userType = daoUser.authentication(username, password);
        if (userType != null) {
            if (userType.equals((String) "Admin")) {
                controller.goAdminPanel(username);
            } else {
                controller.goHome(username);
            }
        } else {
            ButtonType response = alertManager.showAlert("Errore", "Username o password errati", "ERROR");
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
