package dao.implementation;

import static dao.connection.DbConnection.getConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dao.interfaces.DaoGame;
import java.util.LinkedHashMap;

/**
 * @class DaoGameImpl
 * @brief Implementazione dell'interfaccia 'DaoGame' per l'interazione con la
 * tabella "partita" all'interno del database
 *
 * La classe fornisce i metodi necessari per accedere e manipolare i dati
 * relativi alle partite presenti nel database.
 *
 * Tramite l'incapsulamento dell'inteerazione con il database e la separazione
 * della logica di business dalla gestione dei dati viene completamente
 * rispettato il Data Access Object pattern.
 *
 * Le operazioni che fa la classe includono: l'inserimento di un punteggio, il
 * recupero dei migliori punteggi e altre funzionalità utili per ottenere
 * informazioni sui punteggi degli utenti.
 *
 */
public class DaoGameImpl implements DaoGame {

    /**
     * @brief Inserisce un nuovo punteggio all'interno del database.
     *
     * Il metodo inserisce i dati di una nuova partita(difficoltà, punteggio
     * utente) nella tabella
     *
     * @param difficulty la difficoltà della partita
     * @param punteggio il punteggio ottenuto nell'ultima partita
     * @param username il nome utente dell'utente che ha giocata
     * @throws Exception se si verifica un errore durante l'inserimento dei dati
     * nel database
     *
     * @pre {@code difficulty},{@code punteggio} e {@code username} devono
     * essere validi e not null
     * @post la partita viene inserita correttamente nel database
     *
     */
    @Override
    public void inserisci(String difficulty, int punteggio, String username) throws Exception {
        String sql = "INSERT INTO public.partita(\n"
                + " difficolta, punteggio, utente,data)\n"
                + "	VALUES ( ?, ?, ?,?);";
        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, difficulty);
            ps.setInt(2, punteggio);
            ps.setString(3, username);
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(System.currentTimeMillis());
            ps.setTimestamp(4, currentTimestamp);
            ps.executeUpdate();
        }
    }

    /**
     * @brief Recupera il punteggio più alto per un determinato utente.
     *
     * Il metodo esegue una query per ottenere il punteggio massimo di un
     * determinato utente nel database. Nel caso in cui l'utente non abbia mai
     * giocato il metodo restituisce -1
     *
     * @param username il nome dell'utente di cui si desidera ottenere il
     * punteggio massimo
     * @return il punteggio più alto dell'utente, o -1 se l'utente non ha mai
     * giocato
     * @throws Exception se si verifica un errore di lettura dal database
     *
     * @pre {@code difficulty},{@code punteggio} e {@code username} devono
     * essere validi e not null
     * @post il punteggio massimo viene restituito correttamente
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
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero del punteggio migliore", ex);
        }

        return points;
    }

    /**
     * @brief Recupera i primi 20 utenti presenti nel database con il punteggio
     * più alto.
     *
     * Il metodo esegue una query per ottenere i primi 20 utenti con il
     * punteggio più alto. I risultati ottenuti vengono ordinati in ordine
     * decrescente per punteggio
     * @return una mappa che contiene il nome dell'utente e il suo miglior
     * punteggio
     * @throws Exception, nel caso in cui si verifica un errore durante la
     * lettura dei dati dal database
     */
    @Override
    public LinkedHashMap<String, Integer> getRanking() throws Exception {
        LinkedHashMap<String, Integer> topThree = new LinkedHashMap<>();
        String sql = "SELECT utente, MAX(punteggio) AS max_punteggio "
                + "FROM public.partita "
                + "GROUP BY utente "
                + "ORDER BY max_punteggio DESC "
                + "LIMIT 20;";

        int row = 0;

        try (Connection c = getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next() && row < 20) {
                    String utente = rs.getString("utente");
                    int punteggio = rs.getInt("max_punteggio");
                    topThree.put(utente, punteggio);
                    row++;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Errore durante il recupero della top three", ex);
        }

        return topThree;
    }

    /**
     * @brief Recupera il numero di partite giocate da un utente.
     *
     * @param username il nome dell'utente di cui si desidera ottenere il numero
     * di partite
     * @return il numero di partite giocate dall'utente
     * @throws Exception se si verifica un errore durante la lettura dei dati
     * dal database
     *
     * @pre {@code Username} deve essere valido e non nullo
     * @post il numero di partite viene correttamente recuperato
     */
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

    /**
     * @brief Recupera il punteggio dellì'ultimo match giocato da un utente,
     * ordinato per data.
     *
     * @param username il nome dell'utente di cui si desidera ottenere il
     * punteggio dell'ultimo match
     * @return il punteggio dell'ultimo match giocato dall'utente, oppure -1 se
     * l'utente non ha mai giocato
     * @throws Exception se si verifica un errore durante la lettura dei dati
     * dal database
     *
     * @pre {@code Username} deve essere valido e non nullo
     * @post il punteggio dell'ultimo match viene correttamente recuperato
     */
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

    /**
     * @brief Recupera la media dei punteggi di un utente, nel caso in cui l'
     * utente non abbia mai giocato verrà restituito -1.
     *
     *
     * @param username il nome dell'utente di cui si desidera ottenere la media
     * dei punteggi
     * @return la media dei punteggi oppure -1 nel caso in cui l'utente non
     * abbia mai giocato
     * @throws Exception se si verifica un errore durante la lettura dei dati
     * dal database
     *
     * @pre {@code Username} deve essere valido e non nullo
     * @post la media dei punteggi viene correttamente restituita
     */
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
