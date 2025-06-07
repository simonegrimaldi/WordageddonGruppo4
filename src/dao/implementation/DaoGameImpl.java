/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.implementation;

import static dao.connection.DbConnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Game;
import dao.interfaces.DaoGame;

/**
 * La classe {@code DaoGameImpl} implementa l'interfaccia {@link dao.interfaces.DaoGame}
 * ed è responsabile dell'interazione con la tabella {@code partita} nel database.
 * Segue il pattern DAO per incapsulare l'accesso ai dati relativi alle partite.
 */

public class DaoGameImpl implements DaoGame {

    Connection c = null;
    
    /**
     * Inserisce un oggetto game nel database. 
     * @param g è l'oggetto da inserire
     * @throws Exception se si verifica un errore durante l'inserimento nel database
     * @pre g != null
     * @post il match è correttamente inserito nel database nella tabella associata
     */
    
    @Override
    public void inserisci(Game g) throws Exception {
        String sql = "INSERT INTO public.partita(\n"
                + "id, difficolta, punteggio, utente)\n"
                + "	VALUES (?, ?, ?, ?);";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, g.getId());
            ps.setObject(2, g.getDifficulty());
            ps.setInt(3, g.getPoints());
            ps.setString(4, g.getUsername());
            ps.executeUpdate();
        }
    }

    /**
     * Ottiene i punti relativi ad una determinata partita 
     * @param id l'id della partita a cui si fa riferimento
     * @throws Exception se si verifica un errore durante l'inserimento nel database
     * @pre id != null || id non esistente nella tabella
     * @post Il metodo restituisce il punteggio associato all'ID se esistente
     */
    @Override
    public String getPoints(int id) throws Exception {
        String sql = String.format("SELECT punteggio FROM public.partita\n" 
                + "where id = '%s';",id);
        String points = null;
        
         try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            points = rs.getString("punteggio");
         } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return points;
    }

}
