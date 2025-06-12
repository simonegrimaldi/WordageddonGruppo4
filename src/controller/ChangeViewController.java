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
import model.AnalysisImpl;
import model.Quiz;
import model.QuizBuilder;

/**
 * @class ChangeViewController
 *
 * @breif Gestisce la navigazione tra le varie schermate dell'applicazione
 *
 * La classe è responsabile della navigazione tra le schermate
 * dell'applicazione. Implementa l'interfaccia {@link ChangeView} e fornisce i
 * metodi necessari per il caricamento e il cambiamento delle viste.
 *
 * Inoltre, la classe gestisce anche l'inizializzazione dei controller per ogni
 * schermata, passando le dipendenze necessarie e settando i dati dell'utente
 * per ciascuna vista.
 *
 * NOTA: la documentazione dei metodi che forniscono un implementazione concreta
 * dei metodi definiti nell'interfaccia è presenta nell'interfaccia
 * {@link ChangeView}
 *
 */
public class ChangeViewController implements ChangeView {

    private Stage primaryStage;
    private String username;
    private String difficulty;
    private Quiz quiz;

    /**
     * @brief Costruttore del {@code ChangeViewController}.
     *
     * Inizializza il controller con il {@code PrimaryStage} che gestisce le
     * varie scene dell'applicazione.
     *
     * @pre {@code primaryStage} non è null
     * @post il controller è pronto a gestire il cambio viste
     * @param primaryStage Lo stage principale su cui impostare le varie scene
     */
    public ChangeViewController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public void goSignUp() {
        try {
            show("SignUp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goLogIn() {
        try {
            show("LogIn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goAdminPanel(String superUsername) {
        this.username = superUsername;
        try {
            show("AdminPanel");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goHome(String username) {
        this.username = username;
        try {
            show("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean goReading(String difficulty) {
        this.difficulty = difficulty;
        QuizBuilder quizBuilder = new QuizBuilder(new IOTextsImpl(), new IOAnalysisImpl(), username);
        this.quiz = quizBuilder.creaQuiz(difficulty);
        if (quiz == null) {
            return false;
        }
        try {
            show("Reading");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void goQuestion() {
        try {
            show("Question");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void goEntry() {
        try {
            show("Entry");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Carica una vista FXML dal nome specificato, imposta il controller
     * appropriato e aggiorna la scena.
     *
     * Il metodo carica il file FXML specificato, imposta il controller
     * appropriato e procede ad aggiornare la scena
     *
     * @pre L'{@code fxml} è uno tra : {Home, LogIn, SignUp, AdminPanel,
     * Reading, Question}
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

            case "Entry":
                EntryController entryController = loader.getController();
                entryController.setChangeViewController(this);
                break;

            case "Home":
                HomeController homeController = loader.getController();
                 {
                    try {
                        homeController.setChangeViewController(this, username, new DaoGameImpl());
                    } catch (Exception ex) {
                        Logger.getLogger(ChangeViewController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "LogIn":
                LogInController loginController = loader.getController();
                loginController.setChangeViewController(this, new DaoUserImpl());
                break;

            case "SignUp":
                SignUpController signUpController = loader.getController();
                signUpController.setChangeViewController(this, new DaoUserImpl());
                break;

            case "AdminPanel":
                AdminPanelController adminPanelController = loader.getController();
                adminPanelController.setChangeViewController(this, username, new IOFileImpl(), new AnalysisImpl(), new IOAnalysisImpl());
                break;

            case "Reading":
                ReadingController readingController = loader.getController();
                readingController.setChangeViewController(this, username, difficulty, new IOFileImpl(), quiz);
                break;

            case "Question":
                QuestionController questionController = loader.getController();
                questionController.setChangeViewController(this, new DaoGameImpl(), quiz, username, difficulty);
                break;

            default:
                System.out.println("Unknown FXML: " + fxml);
                break;
        }
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
