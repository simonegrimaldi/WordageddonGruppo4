/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
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

    private File selectedFile; //ci salvo il riferimento al file caricato
    private AlertManager alertManager = new AlertManager();
    private String superUsername;

    private ChangeView controller;

    public void setChangeViewController(ChangeView controller, String superUsername) {
        this.controller = controller;
        this.superUsername = superUsername;

        if (this.superUsername != null) {
            titleLabel.setText("Hello " + this.superUsername + " :)");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        titleLabel.setText("Hello admin :)");
    }

    @FXML
    private void logoutButtonClick(ActionEvent event) {
        if (controller != null) {
            controller.goLogIn();
        } else {
            System.out.println("⚠️ controller è null");
        }
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
                alertManager.showAlert("FORMATO NON VALIDO", "Puoi selezionare solo file con estensione '.txt' ");
                return;
            }

            //salvo il riferimento al file selezionato se è .txt
            this.selectedFile = selected;

            //Aggiorno il label
            choseFile.setText("File selezionato: " + selected.getName());
            openFileChooser.setVisible(false);
        }
    }

    @FXML
    private void undoButtonClick(ActionEvent event) {
        this.selectedFile = null; //elimino il riferimento al file selezionato
        textArea.clear(); //pulisco la lista di stopwords
        choseFile.setText("Click here to select a file:"); //resetto il labelnell
        openFileChooser.setVisible(true);
    }

    @FXML
    private void confirmButtonClick(ActionEvent event) {
        //il confirButton può essere premuto solo dopo aver selezionato un file
        if (this.selectedFile == null) {
            alertManager.showAlert("ERRORE", "Prima di confermare devi selezionare un file");
            return;
        }

        //lista che conterrà le stopwords
        Set<String> stopwords = new HashSet<>();
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

        //Lettura del contenuto del file selezionato
        List<String> lines;
        try {
            lines = Files.readAllLines(this.selectedFile.toPath(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            alertManager.showAlert("ERRORE LETTURA FILE", "Impossibile leggere il file selezionato");
            return;
        }

        //Una volta raccolte tutte le righe del testo, conto le parole contenute nelle linee
        int wordCounter = 0;
        for (String line : lines) {
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty() && !stopwords.contains(word.toLowerCase())) {
                    wordCounter++;
                }
            }
        }

        //Classificazione della difficoltà del testo in base al numero di parole
        //senza stopwords
        String difficulty = null;
        if (wordCounter <= 250) {
            difficulty = "facile";
        } else if (wordCounter > 250 && wordCounter <= 750) {
            difficulty = "medio";
        } else {
            difficulty = "difficile";
        }

        // Percorso di destinazione
        File destDir = new File("testi/" + difficulty);
        if (!destDir.exists()) {
            destDir.mkdirs(); 
        }

        File destFile = new File(destDir, selectedFile.getName());
        try {
            Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Percorso file salvato: " + destFile.getAbsolutePath());
            alertManager.showAlert("File caricato con successo","Il file \"" + selectedFile.getName() + "\" è stato salvato nella cartella: \"" + difficulty + "\".\n\nParole significative trovate: " + wordCounter);
        } catch (IOException e) {
            alertManager.showAlert("Errore salvataggio", "Impossibile copiare il file nella cartella " + difficulty);
            return;
        }

        //ultima istruzione che viene eseguita, per resettare l'adminPanel
        controller.goAdminPanel(this.superUsername);
    }

}
