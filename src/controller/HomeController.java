/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import dao.interfaces.DaoGame;
import java.net.URL;
import java.util.HashMap;
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
 * FXML Controller class
 *
 * @author simonegrimaldi
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
    private Label ranking;
    @FXML
    private Label scoreLabel;
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
    private boolean isVisibleProfile = false;
    private boolean isVisiblePlay = false;
    private DaoGame daoGame;
    private AlertManager alertManager = new AlertManager();
    private ObservableList<String[]> rankingObs = FXCollections.observableArrayList();

    public void setChangeViewController(ChangeView controller, String username, DaoGame daoGame) throws Exception {
        this.controller = controller;
        this.daoGame = daoGame;
        this.username = username;
        title.textProperty().setValue("Hello " + username + " !");
        Integer bestScore = daoGame.getBestPointsPoints(username);
        if (bestScore == -1) {
            scoreLabel.setText("Non hai mai giocato! Cosa aspetti ?");
        }
        else {
            scoreLabel.setText("Your best score : " + bestScore);
        }
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

        MenuItem easy = new MenuItem("Facile");
        MenuItem medium = new MenuItem("Media");
        MenuItem hard = new MenuItem("Difficile");
        easy.setOnAction(e -> difficultyChooser.setText("Facile"));
        medium.setOnAction(e -> difficultyChooser.setText("Media"));
        hard.setOnAction(e -> difficultyChooser.setText("Difficile"));
        difficultyChooser.getItems().addAll(easy, medium, hard);
    }

    @FXML
    private void logoutButtonClick(ActionEvent event
    ) {
        controller.goLogIn();
    }

    @FXML
    private void profileButtonClick(ActionEvent event
    ) {
        if (!profileContainer.isVisible()) {
            profileContainer.setVisible(true);
            playContainer.setVisible(false);
        } else {
            profileContainer.setVisible(false);
        }
    }

    @FXML
    private void playButtonClick(ActionEvent event
    ) {
        if (!playContainer.isVisible()) {
            playContainer.setVisible(true);
            profileContainer.setVisible(false);
        } else {
            playContainer.setVisible(false);
        }
    }

    @FXML
    private void startGameClick(ActionEvent event
    ) {
        String selectedDifficulty = difficultyChooser.getText();

        switch (selectedDifficulty.toLowerCase()) {
            case "difficulty":
                alertManager.showAlert("ERRORE", "Scegliere un livello di difficoltà!");
                break;
            default:
                controller.goReading(selectedDifficulty);
                return;
        }
    }

    private void setRankingTable() throws Exception {
        HashMap<String, Integer> topThree = daoGame.getTopThree();
        int position = 1;
        for (Map.Entry<String, Integer> entry : topThree.entrySet()) {
            String usr = entry.getKey();
            Integer pt = entry.getValue();
            if (!usr.equals("") && pt != -1) {
                rankingObs.add(new String[]{String.valueOf(position), usr, String.valueOf(pt)});
            }
            position++;

        }

        positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0]));
        usernameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1]));
        pointsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2]));

        rankingTable.setItems(rankingObs);
    }
    
    public void setStatistics() throws Exception {
        int numGame= daoGame.getNumberGame(username);
        int pointsLastGame = daoGame.getLastMatch(username);
        double averageGame = daoGame.getAverageMatch(username);
        
        System.out.println("Last Game Points: " + pointsLastGame);
        
        statisticsLabel.textProperty().setValue("Your Score \n Game Played : " + numGame + "\n"
                + "Average Points : " + averageGame + "%" + "\n" 
                + "Last Game Played : " + pointsLastGame + "/100");
    }
}
