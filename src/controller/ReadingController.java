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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import model.Quiz;
import util.AlertManager;

/**
 * @class ReadingController
 * @brief Controller per la schermata di lettura
 *
 * Questo controller è responsabile della gestione della schermata di lettura
 * del test appena iniziato il quiz. Il controller si occupa della
 * visualizzazione del testo da leggere, della gestione del timer per il tempo
 * di lettura e l'aggiornamento della barra di progresso che rappresenta il
 * passare del tempo. Permette inoltre all'utente di confermare la lettura e
 * procedere con la sezione relativa alle domande.
 *
 * La classe include un timer che varia in base alla difficoltà di gioco
 * selezionata, fornendo allo stesso tempo un feedback visivo del tempo
 * rimanente. Allo scadere del tempo l'utente viene automaticamente
 * reindirizzato alla schermata che contiene i quesiti.
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

    private AlertManager alertManager = new AlertManager();
    private ChangeView controller;
    private Timeline timeline;
    private int timer = 0;
    private String difficulty;
    private Quiz quiz;
    private IOFile ioFile;

    /**
     * @brief Imposta il controller di navigazione e i dati necessari alla
     * schermata di lettura.
     *
     * In particolare modo, questo metodo: imposta il controller, il nome
     * utente, la difficoltà, l'oggetto quiz e l'interfaccio IOFile. Inoltre
     * carica il testo che l'utente dovrà legger e fa partire il timer.
     *
     * @param controller per la navigazione tra le schermate
     * @param username_read il nome utente di colui che sta leggendo il testo
     * @param difficulty la difficoltà selezionata per il quiz
     * @param ioFile l'interfaccia per il caricamento dei file
     * @param quiz l'ogetto quiz che contiene i testi da leggere
     */
    public void setChangeViewController(ChangeView controller, String username_read, String difficulty, IOFile ioFile, Quiz quiz) {
        this.controller = controller;
        this.username_read = username_read;
        this.difficulty = difficulty;
        this.quiz = quiz;
        this.ioFile = ioFile;
        caricaTesti();
        startTimer();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    /**
     * @brief Gestisce il click sul pulsante di chiusura
     *
     * Quando l'utente clicca il tasto per chiudere il quiz, il timer viene
     * fermato e il giocatore viene reindirizzato alla schermata Home
     *
     * @param event
     */
    @FXML
    private void closeButtonClick(ActionEvent event) {
        // Chiamata al metodo showAlert per chiedere conferma
        ButtonType response = alertManager.showAlert("Attenzione", "Sei sicuro di voler uscire?", "CONFIRMATION");

        // Se l'utente ha premuto "OK", ferma il timer e torna alla schermata Home
        if (response == ButtonType.OK) {
            if (timeline != null) {
                timeline.stop();
            }
            controller.goHome(username_read);
        } else {
            // Se l'utente ha premuto "Annulla", non fare nulla
            System.out.println("L'utente ha deciso di non uscire.");
        }
    }

    /**
     * @brief Gestisce il click sul pulsante di conferma
     *
     * Quando l'utente clicca sul pulsante di conferma, il timer viene fermato e
     * l'utente viene reindirizzato alla schermata contenente le domande
     *
     * @param event
     */
    @FXML
    private void confirmButtonClick(ActionEvent event) {
        if (timeline != null) {
            timeline.stop();
        }
        controller.goQuestion();
    }

    /**
     * @brief metodo per la formattazione del tempo in formato mm:ss
     * @param totalSeconds il numero totale di secondi da formattare
     * @return la stringa formattata nel formato mm:ss
     */
    private String formatTime(int totalSeconds) {
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * @brief Avvia il timer che rappresenta il tempo di lettura
     *
     * Il metodo avvia una "Timeline" che conta il tempo rimanente per la
     * lettura, aggiornando ogni secondo il timer e la barra di progresso. La
     * quantità di tempo a disposizione cambia in base alla difficoltà scelta
     * dal giocatore.
     *
     * Quando mancano 30 secondi alla fine del tempo, il timer diventa rosso,
     * allo scadere di questo tempo il confirm button viene "premuto" in
     * automatico dall'applicazione.
     */
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

        progressBar.getStyleClass().removeAll("warning");
        if (!progressBar.getStyleClass().contains("normal")) {
            progressBar.getStyleClass().add("normal");
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            if (timer >= 0) {
                timerLabel.setText(formatTime(timer));

                double progress = (double) (totalSeconds - timer) / totalSeconds;
                progressBar.setProgress(Math.min(progress, 1.0));

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

    /**
     * @brief Carica il testo da leggere
     *
     * Il metodo carica il testo da leggere servendosi dell'interfaccia IOFile,
     * che restituisce il contenuto del file. Il testo viene quindi mostrato
     * nell'area di testo della schermata di lettura.
     */
    public void caricaTesti() {
        System.out.println(quiz.getTesti());
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
