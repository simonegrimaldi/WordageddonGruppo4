
package controller;

import util.AlertManager;
import dao.implementation.DaoUserImpl;
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
 * Controller per la schermata di login.
 * Questa classe permette di gestire l'interfaccia di login dell'utente,
 * utilizzando un controllo delle credenziali che sfrutta la classe `DaoUserImpl`
 * e gestisce la navigazione tra le schermate (Home, SignUp, etc.).
 * 
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
    DaoUserImpl daoUser=new DaoUserImpl();
    ChangeView controller;
       public void setChangeViewController(ChangeView controller) {
        this.controller = controller;
    }

   
    

    /**
     * Initializes the controller class.
     * For now no other inizialization are needed
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }    
    
    /**
     * Metodo che gestisce il click sul button di signUp.
     * In qualsiasi momento il bottone viene premuto la vista cambia
     * e si passa alla schermata di registrazione
     * @param event 
     */
    @FXML
    private void SignUpButtonClick(ActionEvent event) {
        controller.goSignUp();
    }

    /**
     * Metodo che gestisce il click sul button di Login.
     * 
     * Nel momento in cui sono stati inseriti username e password, verrà effettuato
     * un controllo per verificare l'effettiva corrispondenza con i dati presenti
     * nel database, in caso di esito positivo, l'User verrà reindirizzato alla 
     * schermata Home; mentre per quanto riguarda l'Admin quest'ultimo verrà 
     * reindirizzato all' AdminPanel. 
     * 
     * Quando uno dei campi username o password viene lasciato vuoto, allora comparirà
     * a video un messaggio di errore.
     * 
     * @param event 
     */
    @FXML
    private void LoginInButtonClick(ActionEvent event) {
        String username=usernameField.getText();
        String password=passwordField.getText();
        
        if(username.isEmpty() || password.isEmpty()){
            alertManager.showAlert("ERRORE", "entrambi i campi Username e Password devono essere compilati");
            return;
        }
    
    //gestione del cambio di view nel caso in cui ad effettuare l'accesso
    //sia un utente semplice o un Admin
    String userType=daoUser.authentication(username, password);
    if (userType!=null) {
        if(userType.equals((String)"Admin")){
            controller.goAdminPanel(username);
        }else{
        controller.goHome(username);
        }
    } else {
        // Mostra errore userType==null
        //questo implica che le credenziali di accesso controllate nel metodo
        //authentication sono errate
        alertManager.showAlert("Errore", "Username o password errati.");
    }
}    
    }
