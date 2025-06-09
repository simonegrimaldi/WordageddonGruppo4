/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import IOOperation.IOFile;
import java.io.File;
import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import model.AnalysisImpl;
import util.AlertManager;

/**
 * FXML Controller class
 *
 * @author simonegrimaldi
 */
public class AdminPanelController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Button openFileChooser;
    @FXML
    private TextArea textArea;
    @FXML
    private Button undoButton;
    @FXML
    private Button confirmButton;
    @FXML
    private Label choseFile;

    private File selectedFile;
    private String superUsername;
    private ChangeView controller;
    private AlertManager alertManager;
    private IOFile ioFile;

    public void setChangeViewController(ChangeView controller, String superUsername, IOFile ioFile) {
        this.controller = controller;
        this.superUsername = superUsername;
        if (this.superUsername != null) 
            titleLabel.setText("Hello " + this.superUsername + " :)");
        
        this.ioFile = ioFile;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alertManager = new AlertManager();
        titleLabel.setText("Hello admin :)");
    }

    @FXML
    private void logoutButtonClick(ActionEvent event) {
        controller.goLogIn();
    }

    @FXML
    private void openFileChooserClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleziona una file .txt");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File selected = fileChooser.showOpenDialog(openFileChooser.getScene().getWindow());

        if (selected != null) {
            if (!selected.getName().toLowerCase().endsWith(".txt")) {
                alertManager.showAlert("FORMATO NON VALIDO", "Puoi selezionare solo file con estensione '.txt' ", "ERROR");
                return;
            }
            this.selectedFile = selected;

            choseFile.setText("File selezionato: " + selected.getName());
            openFileChooser.setVisible(false);
        }
    }

    @FXML
    private void undoButtonClick(ActionEvent event) {
        this.selectedFile = null;
        textArea.clear();
        choseFile.setText("Click here to select a file:");
        openFileChooser.setVisible(true);
    }

    @FXML
    private void confirmButtonClick(ActionEvent event) {
        if (this.selectedFile == null) {
            alertManager.showAlert("ERRORE", "Prima di confermare devi selezionare un file", "ERROR");
            return;
        }

        Set<String> stopwords = new HashSet<>();
        stopwords = createStopWordsSet(stopwords);
        AnalysisImpl a = new AnalysisImpl();
        a.analyzeText(selectedFile, stopwords);
        String difficulty = a.difficulty();

        if (difficulty == null) {
            return;
        }

        File destDir = new File("testi/" + difficulty);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        
        boolean flag = ioFile.saveFile(new File(destDir, selectedFile.getName()), selectedFile);
        if(flag)
            alertManager.showAlert("File caricato con successo", "Il file \"" + selectedFile.getName() + "\" è stato salvato nella cartella: \"" + difficulty, "CONFIRMATION");
        else
            alertManager.showAlert("Errore salvataggio", "Impossibile copiare il file nella cartella " + difficulty, "ERROR");

        controller.goAdminPanel(this.superUsername);
    }

    public Set<String> createStopWordsSet(Set<String> stopwords) {

        String text = textArea.getText().trim();

        if (!text.isEmpty()) {
            String[] components = text.split(",");
            for (String parola : components) {
                String cleanedWord = parola.trim();
                if (!cleanedWord.isEmpty()) {
                    stopwords.add(cleanedWord.toLowerCase());
                }
            }
        }
        return stopwords;
    }
}
