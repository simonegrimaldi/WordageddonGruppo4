package controller;

import IOOperation.IOAnalysis;
import IOOperation.IOAnalysisImpl;
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
 * @class AdminPanelController
 * @brief Controller associato alla view AdminPanel. Si occupa di gestire la
 * logica per il caricamento, la validazione e la classificazione dei testi che
 * vengono inseriti dall'amministratore. Implementa anche un meccanismo di
 * gestione delle stopwords (una serie di parole che non devono essere
 * utilizzate per la scelta della difficoltà del testo inserito). Permette
 * infine anche di gestire la navigazione tra le view.
 *
 * In particolare questo controller permette all'admin di: - Inserire un file
 * (rigorosamente in formato .txt) - Inserire una lista opzionale di stopwords
 * (che nel caso in cui vengano inserite devono necessariamente essere inserite
 * separate da virgole) - Classificare in automatico il file in tre livelli di
 * difficoltà (facile, medio e difficile) - Salvare il file nella cartella
 * corrispondente alla difficoltà del testo - Annullare tutte le modifiche fatte
 * nella sessione corrente - Effettuare il logout
 *
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
    private AnalysisImpl analysis = new AnalysisImpl();
    private IOAnalysisImpl ioa = new IOAnalysisImpl();

    /**
     * Imposta il controller di navigazione, l'username dell'admin (utile per
     * far apparire un messaggio personalizzato di benvenuto), e l'interfaccio
     * IO utile per il caricamento dei file.
     *
     *
     * @param controller
     * @param superUsername
     * @param ioFile
     */
    public void setChangeViewController(ChangeView controller, String superUsername, IOFile ioFile) {
        this.controller = controller;
        this.superUsername = superUsername;
        if (this.superUsername != null) {
            titleLabel.setText("Hello " + this.superUsername + " :)");
        }

        this.ioFile = ioFile;
    }

    /**
     * Inizializza i componenti della GUI e imposta un messaggio predefinito di
     * benvenuto.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alertManager = new AlertManager();
        titleLabel.setText("Hello admin :)");
    }

    /**
     * Gestisce il logout dell'admin e il ritorno alla view di login
     *
     * @param event
     */
    @FXML
    private void logoutButtonClick(ActionEvent event) {
        controller.goLogIn();
    }

    /**
     * Apre un file Chooser
     *
     * @param event
     */
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
        int wordCount = analysis.analyzeText(this.selectedFile, stopwords); // Questa riga fa tutto
        String difficulty = analysis.difficulty();

        if (difficulty == null) {
            return;
        }

        File destDir = new File("testi/" + difficulty);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }

        boolean flag = ioFile.saveFile(new File(destDir, selectedFile.getName()), selectedFile);
        if (flag) {
            alertManager.showAlert("File caricato con successo", "Il file \"" + selectedFile.getName() + "\" è stato salvato nella cartella: \"" + difficulty, "CONFIRMATION");
            ioa.saveAnalysis(analysis, "testi/" + difficulty + "/" + this.selectedFile.getName().replace(".txt", "Analisys.bin"));
        } else {
            alertManager.showAlert("Errore salvataggio", "Impossibile copiare il file nella cartella " + difficulty, "ERROR");
        }

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
