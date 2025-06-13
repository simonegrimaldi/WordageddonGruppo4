package controller;

import IOOperation.IOAnalysis;
import IOOperation.IOFile;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import model.Analysis;
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
    private Analysis analysis;
    private IOAnalysis ioa;

    /**
     * Imposta il controller di navigazione, l'username dell'admin (utile per
     * far apparire un messaggio personalizzato di benvenuto), e l'interfaccio
     * IO utile per il caricamento dei file.
     *
     *
     * @param controller
     * @param superUsername
     * @param ioFile
     * @param analysis
     * @param ioa
     */
    public void setChangeViewController(ChangeView controller, String superUsername, IOFile ioFile, Analysis analysis, IOAnalysis ioa) {
        this.controller = controller;
        this.superUsername = superUsername;
        if (this.superUsername != null) {
            titleLabel.setText("Hello " + this.superUsername + " :)");
        }
        this.ioFile = ioFile;
        this.analysis = analysis;
        this.ioa = ioa;
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
     * @brief Apre un file Chooser che consente all'Admin di selezionare un file
     * in formato '.txt'.
     *
     * Nel momento in cui il file selezionato è in un formato valido, allora: -
     * Il button con il simbolo '+' viene nascoto - Appare il nome del file che
     * è stato selezionato
     *
     * Nel caso in cui il File non sia in formato '.txt' non sarà proprio
     * possibile selezionarlo.
     *
     * Infine, viene salvato un "riferimento" al file scelto dall'amministratore
     * che sarà utile in seguito per le operazioni di analisi e salvataggio del
     * file
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

    /**
     * @brief Annulla l'operazione corrente di selezione del file e inserimento
     * delle stopwords.
     *
     * In particolare: - viene rimosso il riferimeno al selectedFile - pulisce
     * la textArea dalle stopwords - il chooseFile viene reimpostato con il
     * messaggio iniziale - il pulsante '+' per aprire il fileChooser viene reso
     * di nuovo visibile
     *
     * Questo button risulta molto utile nel momento in cui l'Admin decide di
     * annullare l'operazione corrente e di ricominciare da capo
     *
     * @param evento
     */
    @FXML
    private void undoButtonClick(ActionEvent event) {
        this.selectedFile = null;
        textArea.clear();
        choseFile.setText("Click here to select a file:");
        openFileChooser.setVisible(true);
    }

    /**
     * @brief Gestisce il click sul bottone di conferma per salvare il file e la
     * sua analisi.
     *
     * In particolar modo possiamo dire che questo metodo esegue i seguenti
     * passi: 1) Verifica se è stato effettivamente selezionato un file, se coì
     * non fosse viene mostrato un messaggio di alert che invita l'Admin a
     * selezionare un file prima di fare click sul bottone di conferma 2) Crea
     * un lista di stopwords, che saranno utili per analizzare il testo 3)
     * Determina la difficoltà del testo che è stato selezionato tramite i
     * metodi della classe {
     * @class Analysis}; la difficoltà viene selezionata in base al numero di
     * parole significative contenute nel file selezionato. NB: per parole
     * significative si intendono tutte le parole del file escluse eventuali
     * stopwords 4) Salva il file nella cartella appropriata (corrispondente
     * alla difficoltà del file) 5) Se il file è stato salvato con successo
     * allora verrà mostrato un messaggio di conferma; nel caso in cui il
     * salvataggio fallisca verrà mostrato un messaggio di errore 6) L'analisi
     * del testo selezionato viene salvata insieme al file corrispondete nella
     * cartella corrispondente alla difficoltà 7) Infine ritorna alla schermata
     * iniziale del pannello amministratore, dando all' Admin la possibilità di
     * aggiungere nuovi file
     *
     * Nota: nel caso in cui l'Admin decida di inserire un testo che era già
     * stato inserito in precedenza(o con lo stesso nome di un file già presente
     * nella cartella testi) non verranno generati duplicati, ma bensì il file
     * verrà sostituito
     *
     *
     * @param event
     */
    @FXML
    private void confirmButtonClick(ActionEvent event) {
        if (this.selectedFile == null) {
            alertManager.showAlert("ERRORE", "Prima di confermare devi selezionare un file", "ERROR");
            return;
        }

        Set<String> stopwords = new HashSet<>();
        stopwords = createStopWordsSet(stopwords);
        int wordCount = analysis.analyzeText(selectedFile, stopwords); // Questa riga fa tutto
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
            alertManager.showAlert("File caricato con successo", "Il file \"" + selectedFile.getName() + "\" è stato salvato nella cartella:\n \"" + difficulty + " \"", "CONFIRMATION");
            ioa.saveAnalysis(analysis, "testi/" + difficulty + "/" + this.selectedFile.getName().replace(".txt", "Analysis.bin"));
        } else {
            alertManager.showAlert("Errore salvataggio", "Impossibile copiare il file nella cartella " + difficulty, "ERROR");
        }

        controller.goAdminPanel(this.superUsername);
    }

    /**
     * @brief Crea un set di stopwords che saranno utili per la scelta della
     * difficoltà del testo e per la sua analisi.
     *
     * Legge il contenuto della textArea, separa le parole usando ',' come
     * delimitatore, e aggiunge la parola al Set di stopwords, dopo averla
     * ripulita da eventuali spazi inutili e resa tutta minuscola. Eventuali
     * "parole vuote" verranno ignorate.
     *
     * La scelta di utilizzare un set ci da la possibilità di evitare stopwords
     * duplicate e quindi eventuali errori durante la fase di analisi del testo.
     *
     *
     * @param stopwords
     * @return
     */
    public Set<String> createStopWordsSet(Set<String> stopwords) {

        String text = textArea.getText().trim();

        try (Scanner s = new Scanner(new BufferedReader(new FileReader("./analyticsFile/stopWordsList.txt")))) {
            String str = null;
            s.useDelimiter(",");
            s.useLocale(Locale.US);
            while (s.hasNext()) {
                if ((str = s.next().trim()) != null) {
                    stopwords.add(str.toLowerCase());
                }
            }
        } catch (IOException ex) {
            return null;
        }

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
