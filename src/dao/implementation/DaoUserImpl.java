package dao.implementation;

import static dao.connection.DbConnection.getConnection;
import static util.PasswordHash.checkPassword;
import static util.PasswordHash.hashPassword;
import dao.interfaces.DaoUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @class DaoUserImpl
 * @brief Implementazione dell'interfaccia DaoUser per la gestione delle
 * operazioni sugli utenti
 *
 * La classe fornisce i metodi per gestire l'autenticazione e la registrazione
 * degli utenti nel database.
 *
 * La classe utilizza il Data Acces Object pattern per separare la logica di
 * accesso ai dati dalla logica applicativa.
 *
 * I metodi presenti nella classe permettono di verificare le credenziali
 * dell'utente, di registrare nuovi utenti e di gestire il recupero e
 * l'archiviazione delle informazioni utente nel database.
 *
 */
public class DaoUserImpl implements DaoUser {

    /**
     * @brief Esegue l'autenticazione dell'utente.
     *
     * Il metodo verifica che le credenziali dell'utente siano presenti nel
     * database. Se le credenziali sono corrette, viene restituito il tipo di
     * utente (admin o user), altrimenti verrà restituito null
     *
     * @param username il nome dell'utente da identificare
     * @param password la password inserita dall'utente
     * @return il tipo di utente se le credenziali sono corrette alrimenti null
     * @throws SQLException se si verifica un errore durante l'interazione con
     * il database
     */
    @Override
    public String authentication(String username, String password) {
        String password_ricevuta = null;
        String tipo = null;
        String sql = "select password, tipo from utente where username=?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                password_ricevuta = rs.getString("password");
                tipo = rs.getString("tipo");
                password_ricevuta = rs.getString("password");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (checkPassword(password, password_ricevuta)) {
            return tipo;
        } else {
            return null;
        }
    }

    /**
     * @brief Registra un nuovo utente
     *
     * Il metodo permette di registrare un nuovo utente nel database. Viene
     * quindi verificato se l'username è gia presente nel database, in caso
     * affermativo il metodo restituisce 'false' , altrimenti verrà inserito un
     * nuovo record nella tabella {@code utente} restituendo 'true'
     *
     * @param username l'username dell'utente che si vuole registrare
     * @param password la password dell'utente che si vuole registrare
     * @return 'true' se l'utente è stao registrato con successo, 'false' se
     * l'username esiste già
     * @throws SQLException se si verifica un errore durante l'inserimento nel
     * database
     *
     * @pre username e password devono essere validi e non nulli
     * @post un nuovo utente viene aggiunto nel database, oppure il tenetativo
     * fallisce se l'username esiste già
     */
    @Override
    public boolean registration(String username, String password) {
        String password_criptata = hashPassword(password);
        boolean esecuzione = false;
        String sql_username = "select username from utente where username=?";
        String Username_ricevuto = null;
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql_username);) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Username_ricevuto = rs.getString("username").toLowerCase();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (username.toLowerCase().equals(Username_ricevuto)) {
            esecuzione = false;
        } else {
            esecuzione = true;
            String sql_insert = "insert into utente(username,password,tipo) values(?,?,?)";
            try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql_insert);) {
                ps.setString(1, username);
                ps.setString(2, password_criptata);
                ps.setString(3, "User");
                ps.executeUpdate();
            } catch (SQLException ex) {
                Logger.getLogger(DaoUserImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return esecuzione;
    }

}
