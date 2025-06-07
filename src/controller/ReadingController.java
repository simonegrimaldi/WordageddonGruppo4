/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import IOoperation.IOFile;
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
    
    ChangeView controller;
    private Timeline timeline;
    private int timer;
    IOFile file;
    
    public void setIOFile(IOFile file){
        this.file=file;
    }
    
    public void setChangeViewController(ChangeView controller,String username_read) {
        this.controller = controller;
        this.username_read = username_read;
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
        timer = 10; // durata in secondi
        progressBar.setProgress(0);
        timerLabel.setText("00:10");
        timerLabel.setStyle("-fx-text-fill: black;");

        if (timeline != null) timeline.stop();

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timer >= 0) {
                timerLabel.setText(String.format("00:%02d", timer));
                double progress = (10 - timer) / 10.0;
                progressBar.setProgress(Math.min(progress, 1.0));

                if (timer < 4) {
                    timerLabel.setStyle("-fx-text-fill: red;");
                }

                timer--;
            } else {
                timeline.stop();
                confirmButton.fire(); 
            }
        }));

        timeline.setCycleCount(11);
        timeline.play();
    }
}
