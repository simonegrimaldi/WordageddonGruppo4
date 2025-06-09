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
import javafx.scene.control.TextArea;
import model.Quiz;

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
    @FXML
    private TextArea stampaTesti;

    private String username_read;
    /**
     * Initializes the controller class.
     */

    private ChangeView controller;
    private Timeline timeline;
    private int timer = 0;
    private String difficulty;
    private Quiz quiz;
    private IOFile ioFile;
    /* 
    public void setIOFile(IOFile file){
        this.file=file;
    }*/

    public void setChangeViewController(ChangeView controller, String username_read, String difficulty,IOFile iOFile, Quiz quiz) {
        this.controller = controller;
        this.username_read = username_read;
        this.difficulty = difficulty;
        this.quiz = this.quiz;
        this.ioFile = ioFile;
        caricaTesti();
        startTimer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void closeButtonClick(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        controller.goHome(username_read);
    }

    @FXML
    private void confirmButtonClick(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        controller.goQuestion();
    }

    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public void startTimer() {
        if (timeline != null) {
            timeline.stop();
        }

        int totalSeconds;

        switch (difficulty.toLowerCase()) {
            case "facile":
                totalSeconds = 600;
                break;
            case "media":
                totalSeconds = 420;
                break;
            case "difficile":
                totalSeconds = 240;
                break;
            default:
                totalSeconds = 600;
        }

        timer = totalSeconds;
        progressBar.setProgress(0);
        timerLabel.setText(formatTime(timer));
        timerLabel.setStyle("-fx-text-fill: black;");

        // Imposta stile iniziale
        progressBar.getStyleClass().removeAll("warning");
        if (!progressBar.getStyleClass().contains("normal")) {
            progressBar.getStyleClass().add("normal");
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timer >= 0) {
                timerLabel.setText(formatTime(timer));

                double progress = (double) (totalSeconds - timer) / totalSeconds;
                progressBar.setProgress(Math.min(progress, 1.0));

                // Quando mancano 30 secondi, cambia lo stile
                if (timer == 30) {
                    timerLabel.setStyle("-fx-text-fill: red;");

                    progressBar.getStyleClass().removeAll("normal");
                    if (!progressBar.getStyleClass().contains("warning")) {
                        progressBar.getStyleClass().add("warning");
                    }
                }

                timer--;
            } else {
                timeline.stop();
                confirmButton.fire();
            }
        }));

        timeline.setCycleCount(totalSeconds + 1);
        timeline.play();
    }

    public void caricaTesti() {
        String content = ioFile.loadFile(quiz.getTesti());

        if (content == null || content.isEmpty()) {
            System.out.println("Nessun contenuto trovato!");
            return;
        }

        stampaTesti.setWrapText(true);
        stampaTesti.setText(content);
        textArea.setContent(stampaTesti);
    }
}