package controller;

import dao.interfaces.DaoGame;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import util.AlertManager;

/**
 * @class HomeController
 *
 * @brief Controller per la schermata principale. Gestisce la visualizzazione
 * del profilo dell'utente, la selezione della difficoltà del gioco, la
 * visualizzazione di una classifica di gioco.
 *
 * Nello specifico questo controller permette all'utente di: - Visualizzare il
 * proprio profilo e le statistiche di gioco - Iniziare una nuova partita
 * scegliendo la difficoltà di gioco
 *
 *
 */
public class HomeController implements Initializable {

    @FXML
    private Button logoutButton;
    @FXML
    private Label title;
    @FXML
    private Button profileButton;
    @FXML
    private Button playButton;
    @FXML
    private VBox playContainer;
    @FXML
    private SplitMenuButton difficultyChooser;
    @FXML
    private Button startGame;
    @FXML
    private Label labelRules;
    @FXML
    private VBox profileContainer;
    @FXML
    private TableView<String[]> rankingTable;
    @FXML
    private TableColumn<String[], String> positionColumn;
    @FXML
    private TableColumn<String[], String> usernameColumn;
    @FXML
    private TableColumn<String[], String> pointsColumn;
    @FXML
    private Label statisticsLabel;

    private String username;
    private ChangeView controller;
    private DaoGame daoGame;
    private AlertManager alertManager = new AlertManager();
    private ObservableList<String[]> rankingObs = FXCollections.observableArrayList();

    /**
     * @brief Imposto il controller per permettere la navigazione tra le
     * schermate L'interfaccia DaoGame che permette di gestire i dati del
     * singolo giocatore e della sessione di gioco.
     *
     * @param controller
     * @param username
     * @param daoGame
     * @throws Exception
     */
    public void setChangeViewController(ChangeView controller, String username, DaoGame daoGame) throws Exception {
        this.controller = controller;
        this.daoGame = daoGame;
        this.username = username;
        title.textProperty().setValue("Hello " + username + " !");
        labelRules.setText(
                " Welcome to Wordageddon!"
                + "The game that trains your mind through reading and memory!\n"
                + "Your goal is to read one or more texts and remember as much information as possible"
                + "in the shortest time. The chosen difficulty level determines the amount of text"
                + "and the time available:\n"
        );
        setRankingTable();
        setStatistics();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        profileContainer.managedProperty().bind(profileContainer.visibleProperty());
        playContainer.managedProperty().bind(playContainer.visibleProperty());

        rankingTable.setSelectionModel(null);

        MenuItem easy = new MenuItem("Easy");
        MenuItem medium = new MenuItem("Medium");
        MenuItem hard = new MenuItem("Hard");
        easy.setOnAction(e -> {
            difficultyChooser.setText("Easy");
            updateLabelRules("Easy");
        });
        medium.setOnAction(e -> {
            difficultyChooser.setText("Medium");
            updateLabelRules("Medium");
        });
        hard.setOnAction(e -> {
            difficultyChooser.setText("Hard");
            updateLabelRules("Hard");
        });
        difficultyChooser.getItems().addAll(easy, medium, hard);
    }

    /**
     * @brief Metodo che gestisce il click sul button di Logout.
     *
     * In qualsiasi momento il bottone viene premuto la vista cambia e si passa
     * alla schermata di LogIn.
     *
     * @param event
     */
    @FXML
    private void logoutButtonClick(ActionEvent event
    ) {
        controller.goLogIn();
    }

    /**
     * @brief Metodo che gestisce il click sul button Profilo. Mostrando o
     * nascondendo il profilo dell'utente
     *
     * @param event
     */
    @FXML
    private void profileButtonClick(ActionEvent event
    ) {
        if (!profileContainer.isVisible()) {
            profileContainer.setVisible(true);
            playContainer.setVisible(false);
        }
    }

    /**
     * @brief Metodo che gestisce il click sul button Play. Mostrando o
     * nascondendo le informazioni relative al gioco
     *
     * @param event
     */
    @FXML
    private void playButtonClick(ActionEvent event) {
        if (!playContainer.isVisible()) {
            playContainer.setVisible(true);
            profileContainer.setVisible(false);
        }
    }

    /**
     * @brief Metodo che gestisce il click sul button Play.
     *
     * Quando viene premuto, vengono verificati i dati inseriti relativamente
     * alla difficoltà, e viene avviata una partita corrispondente alla
     * difficoltà selezionata. Nel caso in cui si voglia avviare una partita
     * senza aver impostato un livello di difficoltà verrà visualizzato un
     * messaggio di errore, stessa cosa che succede nel caso in cui non ci sono
     * testi corrispondenti al livello di difficoltà scelto.
     *
     * @param event
     */
    @FXML
    private void startGameClick(ActionEvent event) {
        String selectedDifficulty = difficultyChooser.getText();

        switch (selectedDifficulty.toLowerCase()) {
            case "difficulty":
                alertManager.showAlert("ERRORE", "Scegliere un livello di difficoltà!", "ERROR");
                break;
            default:
                if (!controller.goReading(selectedDifficulty)) {
                    alertManager.showAlert("ERRORE", "Non ci sono abbastanza testi disponibili per questa difficoltà", "ERROR");
                }
                return;
        }

    }

    /**
     * @brief Imposta la tabella che contiene la classifica, recuperando i
     * migliori punteggi presenti nel database. La classifica conterrà
     * posizione, punteggio e username.
     *
     * @throws Exception, nel caso in cui si presenti un errore nel recupero
     * delle informazioni dal database
     */
    private void setRankingTable() throws Exception {
        LinkedHashMap<String, Integer> topThree = daoGame.getRanking();
        int position = 1;
        for (Map.Entry<String, Integer> entry : topThree.entrySet()) {
            String usr = entry.getKey();
            Integer pt = entry.getValue();
            if (!usr.equals("") && pt != 0) {
                rankingObs.add(new String[]{String.valueOf(position), usr, String.valueOf(pt)});
            }
            position++;

        }

        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        pointsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));
        rankingTable.setItems(rankingObs);
    }

    /**
     * @brief Imposta le stats relative al singolo utente.
     *
     * Le statistiche contengono: - Il numero di partite giocate - Il punteggio
     * dell'ultima partita - La media dei punteggi - Il punteggio dell'ultima
     * partita giocata
     *
     * @throws Exception, nel caso in cui si presenti un errore nel recupero
     * delle informazioni dal database
     */
    public void setStatistics() throws Exception {
        int numGame = daoGame.getNumberGame(username);
        int pointsLastGame = daoGame.getLastMatch(username);
        double averageGame = daoGame.getAverageMatch(username);
        Integer bestScore = daoGame.getBestPointsPoints(username);

        StringBuilder sb = new StringBuilder();
        sb.append("Game Played : ").append(numGame).append("\n");
        if (bestScore == -1) {
            sb.append("Non hai mai giocato! Cosa aspetti ?");
        } else {
            sb.append(String.format("Your record : " + bestScore + "\n"));
        }
        if (averageGame == -1.0) {
            sb.append("Average Points : -\n");
        } else {
            sb.append(String.format("Average Points : %.2f%%\n", averageGame));
        }

        if (pointsLastGame == -1) {
            sb.append("Last Game Played : -\n");
        } else {
            sb.append("Last Game Played : ").append(pointsLastGame).append("/50\n");
        }

        statisticsLabel.setText(sb.toString());
    }

    private void updateLabelRules(String difficulty) {
        String text = "";
        switch (difficulty.toLowerCase()) {
            case "easy":
                text = " Welcome to Wordageddon!"
                        + "The game that trains your mind through reading and memory!\n"
                        + "Your goal is to read one or more texts and remember as much information as possible"
                        + "in the shortest time. The chosen difficulty level determines the amount of text"
                        + "and the time available:\n"
                        + "Easy:\n"
                        + "- One short text (≤ 250 words)\n"
                        + "- Reading time: 5 minutes\n"
                        + "Get ready to challenge your memory!";
                break;
            case "medium":
                text = " Welcome to Wordageddon!"
                        + "The game that trains your mind through reading and memory!\n"
                        + "Your goal is to read one or more texts and remember as much information as possible"
                        + "in the shortest time. The chosen difficulty level determines the amount of text"
                        + "and the time available:\n"
                        + "Medium:\n"
                        + "- Two medium-length texts (250–750 words each)\n"
                        + "- Reading time:  7 minutes\n"
                        + "Get ready to challenge your memory!";
                break;
            case "hard":
                text = " Welcome to Wordageddon!"
                        + "The game that trains your mind through reading and memory!\n"
                        + "Your goal is to read one or more texts and remember as much information as possible"
                        + "in the shortest time. The chosen difficulty level determines the amount of text"
                        + "and the time available:\n"
                        + "Hard:\n"
                        + "- Three long texts (750–1000 words each)\n"
                        + "- Reading time: 9 minutes\n"
                        + "Get ready to challenge your memory!";
                break;
            default:
                text = "Please select a difficulty.";
        }
        labelRules.setText(text);
    }
}
