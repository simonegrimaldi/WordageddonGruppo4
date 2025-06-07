/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import IOOperation.IOFile;
import java.net.URL;
import javafx.util.Duration;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
 */
public class ReadingController implements Initializable {

    @FXML
    private Label timerLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ScrollPane textArea;
    @FXML
    private Button closeButton;
    @FXML
    private Button confirmButton;
    private String username_read;
    /**
     * Initializes the controller class.
     */
    
    private ChangeView controller;
    private Timeline timeline;
    private int timer;
    private IOFile file;
    private String difficulty;
    
    public void setIOFile(IOFile file){
        this.file=file;
    }
    
    public void setChangeViewController(ChangeView controller,String username_read,String difficulty) {
        this.controller = controller;
        this.username_read = username_read;
        this.difficulty=difficulty;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTimer();
    }

    @FXML
    private void closeButtonClick(ActionEvent event) {
        if (timeline != null) timeline.stop();
        controller.goHome(username_read);
    }

    @FXML
    private void confirmButtonClick(ActionEvent event) {
        if (timeline != null) timeline.stop();
        controller.goQuestion();
    }

    private void startTimer() {
    if (timeline != null) timeline.stop();

    int totalSeconds;

    switch (difficulty.toLowerCase()) {
        case "facile":
            totalSeconds = 600; // 10 minuti
            break;
        case "medio":
            totalSeconds = 420; // 7 minuti
            break;
        case "difficile":
            totalSeconds = 240; // 4 minuti
            break;
        default:
            totalSeconds = 600; // default: facile
    }

    timer = totalSeconds;
    progressBar.setProgress(0);
    timerLabel.setText(formatTime(timer));
    timerLabel.setStyle("-fx-text-fill: black;");

    timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
        if (timer >= 0) {
            timerLabel.setText(formatTime(timer));

            double progress = (double) (totalSeconds - timer) / totalSeconds;
            progressBar.setProgress(Math.min(progress, 1.0));

            if (timer <= 30) { // ultimi 30 secondi in rosso
                timerLabel.setStyle("-fx-text-fill: red;");
            }

            timer--;
        } else {
            timeline.stop();
            confirmButton.fire(); // Simula click su conferma
        }
    }));

    timeline.setCycleCount(totalSeconds + 1);
    timeline.play();
}

private String formatTime(int totalSeconds) {
    int minutes = totalSeconds / 60;
    int seconds = totalSeconds % 60;
    return String.format("%02d:%02d", minutes, seconds);
}

}
