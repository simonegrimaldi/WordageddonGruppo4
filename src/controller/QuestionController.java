package controller;

import dao.interfaces.DaoGame;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import util.Question;
import model.Quiz;
import util.AlertManager;

/**
 * @class QuestionController
 * 
 * @brief Controller per la schermata delle domande del quiz. Gestisce la
 * visualizzazione delle domande e delle risposte, raccoglie le risposte
 * selezionate, calcola il punteggio e interagisce con il database per salvare
 * il punteggio ottenuto dall'utente.
 * 
 * il controller gestisce la logica per mostrare le domande e le opzioni di risposta,
 * reccogliere le risposte selezionate dall'utente, calcolare il punteggio ottenuto
 * al quiz, mostrare un messaggio di successo contenente il punteggio ottenuto
 * dal giocatore, salvare il punteggio ottenuto dall'utente sul database. 
 * 
 * Come caso eccezionale il giocatore può anche scegliere di annullare la partita,
 * in questo caso verrà annullata la partita e sarà come se il giocatore non l'abbia
 * mai iniziata. 
 * 
 *
 */
public class QuestionController implements Initializable {

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    /**
     * Initializes the controller class.
     */
    ChangeView controller;
    @FXML
    private Label question1;
    @FXML
    private RadioButton A1;
    @FXML
    private RadioButton C1;
    @FXML
    private RadioButton B1;
    @FXML
    private RadioButton D1;
    @FXML
    private Label question2;
    @FXML
    private RadioButton A2;
    @FXML
    private RadioButton C2;
    @FXML
    private RadioButton B2;
    @FXML
    private RadioButton D2;
    @FXML
    private Label question3;
    @FXML
    private RadioButton A3;
    @FXML
    private RadioButton C3;
    @FXML
    private RadioButton B3;
    @FXML
    private RadioButton D3;
    @FXML
    private Label question4;
    @FXML
    private RadioButton A4;
    @FXML
    private RadioButton C4;
    @FXML
    private RadioButton B4;
    @FXML
    private RadioButton D4;
    @FXML
    private Label question5;
    @FXML
    private RadioButton A5;
    @FXML
    private RadioButton C5;
    @FXML
    private RadioButton B5;
    @FXML
    private RadioButton D5;
    private String difficulty;
    private DaoGame daoGame;
    private Quiz quiz;
    private String username;
    private AlertManager alertManager = new AlertManager();
    @FXML
    private ToggleGroup group1;
    @FXML
    private ToggleGroup group2;
    @FXML
    private ToggleGroup group3;
    @FXML
    private ToggleGroup group4;
    @FXML
    private ToggleGroup group5;

    /**
     * @brief Imposta il controller di navigazione, il quiz, il
     * nome utente e la difficoltà.Recupera e imposta le domande e le risposte
     * dinamicamente.
     * 
     * @param controller
     * @param daoGame
     * @param quiz
     * @param username
     * @param difficulty 
     */
    public void setChangeViewController(ChangeView controller, DaoGame daoGame, Quiz quiz, String username, String difficulty) {
        this.controller = controller;
        this.daoGame = daoGame;
        this.quiz = quiz;
        this.username = username;
        this.difficulty = difficulty;

        List<Question> domande = quiz.getDomande();
        System.out.println("Numero di domande: " + domande.size());

        Label[] questionLabels = {question1, question2, question3, question4, question5};
        RadioButton[][] answerLabels = {{A1, B1, C1, D1}, {A2, B2, C2, D2}, {A3, B3, C3, D3}, {A4, B4, C4, D4}, {A5, B5, C5, D5}};
        for (int i = 0; i < domande.size(); i++) {
            Question q = domande.get(i);

            // Imposta il testo della domanda dinamicamente
            questionLabels[i].setText(q.getQuestionText());
            System.out.println(q.getOptions());
            // Imposta le risposte dinamicamente
            for (int j = 0; j < q.getOptions().size(); j++) {
                answerLabels[i][j].setText(q.getOptions().get(j).toString());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * @brief Metodo che gestisce il click sul pulsante di conferma. Raccoglie le
     * risposte selezionate , calcola il punteggio ottenuto dall'utente e mostra un
     * messaggio di successo. Infine salva il punteggio sul databse.
     * 
     * @param event
     * @throws Exception 
     */
    @FXML
    private void confirmButtonClick(ActionEvent event) throws Exception {
        List<String> selectedAnswers = new ArrayList<>();

        // Recupera la risposta selezionata da ciascun ToggleGroup
        selectedAnswers.add(getSelectedAnswer(group1)); // Risposta per la prima domanda
        selectedAnswers.add(getSelectedAnswer(group2)); // Risposta per la seconda domanda
        selectedAnswers.add(getSelectedAnswer(group3)); // Risposta per la terza domanda
        selectedAnswers.add(getSelectedAnswer(group4)); // Risposta per la quarta domanda
        selectedAnswers.add(getSelectedAnswer(group5)); // Risposta per la quinta domanda
        quiz.setPoints(selectedAnswers);
        int score = quiz.getPoints();

        String message = String.format("Hai completato il quiz con un punteggio di %d", score);
        alertManager.showAlert("Successo", message, "CONFIRMATION");
        daoGame.inserisci(difficulty, quiz.getPoints(), username);
        controller.goHome(username);
    }

    /**
     * @brief Metodo che gestisce il click sul pulsante di annullamento. 
     * 
     * Prima di riportare l'utente alla schermata Home, apparirà un'allert che 
     * chiederà conferma all'utente che quella sia la sua reale intenzione. 
     * 
     * @param event
     * @throws Exception 
     */
    @FXML
    private void cancelButtonClick(ActionEvent event) throws Exception {
        alertManager.showAlert("Termina", "Sei sicuro di voler uscire? \nPerderai tutti i progressi!", "INFORMATION");
        controller.goHome(username);
    }

    private String getSelectedAnswer(ToggleGroup group) {
        RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();

        if (selectedRadioButton != null) {
            return selectedRadioButton.getText();
        }
        return null;
    }

}
