/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao.interfaces;

import java.util.HashMap;
import model.Game;

/**
 * L'interfaccia {@code DaoGame} rappresenta il contratto per il pattern Data
 * Access Object (DAO) associato all'entità {@link Game}. Essa definisce le
 * operazioni fondamentali per interagire con la persistenza dei dati di gioco,
 * nascondendo i dettagli implementativi di accesso al database. E' implementata
 * da {@code DaoGameImpl}.
 */
public interface DaoGame {

    public void inserisci(Game g) throws Exception;

    public Integer getBestPointsPoints(String username) throws Exception;
    
    public HashMap<String,Integer> getTopThree() throws Exception;
    
    public Integer getNumberGame(String username) throws Exception;
    
    public Integer getLastMatch(String username) throws Exception;
    
    public Double getAverageMatch(String username) throws Exception;
}
