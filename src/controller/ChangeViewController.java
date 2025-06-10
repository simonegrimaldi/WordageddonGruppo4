package controller;

import IOOperation.IOAnalysisImpl;
import IOOperation.IOFileImpl;
import dao.implementation.DaoGameImpl;
import dao.implementation.DaoUserImpl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import IOOperation.IOTextsImpl;
import model.Quiz;
import model.QuizBuilder;

/**
 * Il ChangeViewController è responsabile del cambio di view all'interno dell'
 * applicazione JavaFX. Implementa l'interfaccia {@code ChangeView} e fornisce i
 * metodi per navigare tra le diverse schermate.
 */
public class ChangeViewController implements ChangeView {

    private Stage primaryStage;
    private String username;
    private String difficulty;
    private QuizBuilder quizBuilder;
    /**
     * Costruttore del {@code ChangeViewController}.
     *
     * @pre {@code primaryStage} non è null
     * @post il controller è pronto a gestire il cambio viste
     * @param primaryStage Lo stage principale su cui impostare le scene.
     */
    public ChangeViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Carica e mostra la schermata signUp
     *
     * @pre -
     * @post visualizzazione della schermata signUp
     * @param username
     */
    @Override
    public void goSignUp() {
        try {
            show("SignUp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica e mostra la schermata LogIn
     *
     * @pre -
     * @post visualizzazione della schermata LogIn
     * @param username
     */
    @Override
    public void goLogIn() {
        try {
            show("LogIn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica e mostra la schermata AdminPanel
     *
     * @pre l'utente possiede effettua l'accesso con credenziali admin
     * @post visualizzazione della schermata AdminPanel
     * @param username
     */
    @Override
    public void goAdminPanel(String superUsername) {
        this.username = superUsername;
        try {
            show("AdminPanel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica e mostra la schermata home
     *
     * @pre l'utente è autenticato
     * @post visualizzazione della schermata home
     * @param username
     */
    @Override
    public void goHome(String username) {
        this.username = username;
        try {
            show("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica e mostra la schermata per la lettura dei testi
     *
     * @pre l'utente è autenticato
     * @pre l'utente avvia una partita
     * @post visualizzazione della schermata home
     * @param username
     */
    @Override
    public void goReading(String difficulty) {
        this.difficulty = difficulty;
        try {
            show("Reading");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica e mostra la schermata per la risposta alle domande
     *
     * @pre l'utente è autenticato
     * @pre l'utente avvia una partita
     * @post visualizzazione della schermata home
     * @param username
     */
    @Override
    public void goQuestion() {
        try {
            show("Question");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Carica una vista FXML dal nome specificato, imposta il controller e
     * aggiorna la scena.
     *
     * @pre {@code fxml} è uno tra : {Home, LogIn, SignUp, AdminPanel, Reading,
     * Question}
     * @post la scena corrente è aggiornata con la nuova vista e il relativo
     * controller inizializzato.
     *
     * @param fxml Il nome del file FXML da caricare (senza estensione).
     * @param username l’username da passare al controller, se necessario.
     * @throws IOException Se il file FXML non può essere caricato.
     */
    private void show(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/" + fxml + ".fxml"));
        Parent root = loader.load();

        switch (fxml) {
            case "Home":
                HomeController homeController = loader.getController();
            {
                try {
                    homeController.setChangeViewController(this,username,new DaoGameImpl());
                } catch (Exception ex) {
                    Logger.getLogger(ChangeViewController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;


            case "LogIn":
                LogInController loginController = loader.getController();
                loginController.setChangeViewController(this,new DaoUserImpl());
                break;

            case "SignUp":
                SignUpController signUpController = loader.getController();
                signUpController.setChangeViewController(this, new DaoUserImpl());
                break;

            case "AdminPanel":
                AdminPanelController adminPanelController = loader.getController();
                adminPanelController.setChangeViewController(this, username, new IOFileImpl());
                break;

            case "Reading":
                ReadingController readingController = loader.getController();
                this.quizBuilder = new QuizBuilder(new IOTextsImpl(),new IOAnalysisImpl(),username);
                readingController.setChangeViewController(this,username,difficulty,new IOFileImpl(), quizBuilder.creaQuiz(difficulty));
                //readingController.setIOFile(new IOFileClass());
                break;

            case "Question":
                QuestionController questionController = loader.getController();
                questionController.setChangeViewController(this);
                break;

            default:
                System.out.println("Unknown FXML: " + fxml);
                break;
        }

        // Crea e imposta la scena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}