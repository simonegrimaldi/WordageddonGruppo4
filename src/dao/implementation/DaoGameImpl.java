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
import java.util.HashMap;
import java.util.Map;

/**
 * La classe {@code DaoGameImpl} implementa l'interfaccia
 * {@link dao.interfaces.DaoGame} ed è responsabile dell'interazione con la
 * tabella {@code partita} nel database. Segue il pattern DAO per incapsulare
 * l'accesso ai dati relativi alle partite.
 */
public class DaoGameImpl implements DaoGame {

    /**
     * Inserisce un oggetto game nel database.
     *
     * @param g è l'oggetto da inserire
     * @throws Exception se si verifica un errore durante l'inserimento nel
     * database
     * @pre g != null
     * @post il match è correttamente inserito nel database nella tabella
     * associata
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
     * Ottiene il punteggio più alto di un utente in un match.
     *
     * @param username l'username unico dell'utente a cui si fa riferimento
     * @throws Exception se si verifica un errore durante l'inserimento nel
     * database
     * @pre username != null
     * @post Il metodo restituisce il punteggio più alto dell'utente o -1 se non
     * ha mai giocato.
     */
    @Override
    public Integer getBestPointsPoints(String username) throws Exception {
        String sql = "SELECT max(punteggio) AS max_p FROM public.partita WHERE utente = ?;";
        int points = -1;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    points = rs.getInt("max_p");
                    if (rs.wasNull()) {
                        points = -1;
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero del punteggio migliore", ex);
        }

        return points;
    }

    @Override
    public HashMap<String, Integer> getTopThree() throws Exception {
        HashMap<String, Integer> topThree = new HashMap<>();
        String sql = "SELECT utente, MAX(punteggio) AS max_punteggio\n"
                + "FROM public.partita\n"
                + "GROUP BY utente\n"
                + "ORDER BY max_punteggio DESC\n"
                + "LIMIT 3;";
        int row = 0;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next() && row < 3) {
                    if (rs.wasNull()) {
                        topThree.put(" ", -1);
                    }
                    topThree.put(rs.getString("utente"), rs.getInt("max_punteggio"));
                    row++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero della top three", ex);
        }

        return topThree;
    }

    @Override
    public Integer getNumberGame(String username) throws Exception {
        String sql = "SELECT count(*) AS numGame FROM public.partita WHERE utente = ?;";
        int numGame = 0;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numGame = rs.getInt("numGame");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero del numero di game", ex);
        }
        return numGame;
    }

    @Override
    public Integer getLastMatch(String username) throws Exception {
        String sql = "SELECT punteggio FROM public.partita WHERE utente = ? order by data desc LIMIT 1;";
        int numGame = -1;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    numGame = rs.getInt("punteggio");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero dell'ultimo match", ex);
        }
        return numGame;
    }

    @Override
    public Double getAverageMatch(String username) throws Exception {
        String sql = "SELECT avg(punteggio) as avgPt FROM public.partita WHERE utente = ?;";
        Double averageGame = -1.0;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    averageGame = rs.getDouble("avgPt");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero dell'ultimo match", ex);
        }
        return averageGame;
    }
}
