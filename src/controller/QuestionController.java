/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import model.Question;
import model.Quiz;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
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
    
    public void setChangeViewController(ChangeView controller, DaoGame daoGame,Quiz quiz,String username, String difficulty) {
        this.controller = controller;
        this.daoGame = daoGame;
        this.quiz = quiz;
        this.username=username;
        this.difficulty=difficulty;
        
        List<Question> domande = quiz.getDomande();
        System.out.println("Numero di domande: " + domande.size());

        Label[] questionLabels = {question1, question2, question3, question4,question5};
        RadioButton[][] answerLabels = {{A1, B1, C1,D1}, {A2, B2, C2,D2}, {A3, B3, C3,D3}, {A4, B4, C4,D4},{A5, B5, C5,D5}};
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
        int score=quiz.getPoints();
        
        String message = String.format("Hai completato il quiz con un punteggio di %d", score);
        alertManager.showAlert("Successo", message, "CONFIRMATION");
        daoGame.inserisci(difficulty, quiz.getPoints(), username);
        controller.goHome(username);
    }

    @FXML
    private void cancelButtonClick(ActionEvent event) throws Exception {
        alertManager.showAlert("Termina", "Sei sicuro di voler uscire? \nPerderai tutti i progressi!","INFORMATION");
        controller.goHome(username);
    }
    
    private String getSelectedAnswer(ToggleGroup group) {
    // Ottieni la risposta selezionata dal ToggleGroup
    RadioButton selectedRadioButton = (RadioButton) group.getSelectedToggle();
    
    // Se una risposta è selezionata, restituisci il testo della risposta
    if (selectedRadioButton != null) {
        return selectedRadioButton.getText();
    }
    
    // Se nessuna risposta è selezionata, restituisci null
    return null;
}
    
}
