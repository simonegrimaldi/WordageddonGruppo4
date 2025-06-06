/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao.implementation;

import dao.connection.dbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Game;
import dao.interfaces.DaoGame;

/**
 *
 * @author simonegrimaldi
 */
public class DaoGameImpl implements DaoGame {

    Connection c = null;

    @Override
    public void inserisci(Game m) throws Exception {
        String sql = "INSERT INTO public.partita(\n"
                + "id, difficolta, punteggio, utente)\n"
                + "	VALUES (?, ?, ?, ?);";
        try (Connection c = dbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, m.getId());
            ps.setObject(2, m.getDifficulty());
            ps.setInt(3, m.getPoints());
            ps.setString(4, m.getUsername());
            ps.executeUpdate();
        }
    }

    @Override
    public String getPoints(int id) throws Exception {
        String sql = String.format("SELECT punteggio FROM public.partita\n" 
                + "where id = '%s';",id);
        String points = null;
        
         try (Connection c = dbConnection.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            points = rs.getString("punteggio");
         } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return points;
    }

}
