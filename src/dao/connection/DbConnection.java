package dao.connection;

import java.sql.*;

/**
 * @class DbConnection
 * @brief Classe di gestione della connessione al database PostgreSQL
 *
 * La classe si occupa di gestire la connessione al database. Fornisce un metodo
 * statico per ottenere la connessione al database e garantisce che venga creata
 * una sola connessione riutilizzabile durante l'intero ciclo di vita
 * dell'applicazione, in questo modo evitiamo la creazione di connessioni
 * multiple, migliorando quindi l'efficienza dell'applicazione.
 *
 */
public class DbConnection {

    private static final String URL = "jdbc:postgresql://dpg-d10ml63ipnbc73904k90-a.oregon-postgres.render.com:5432/wordageddon_db?sslmode=require";
    private static final String USER = "wordageddon_db_user";
    private static final String PASS = "D5ZMew4bDG2DTU6v126sNH7JvSFLFYMP";
    private static Connection c = null;

    /**
     * @brief Restituisce la connessione al database
     *
     * Il metodo controlla se la connessione al databse è gia esistente e se lo
     * è se è ancora attiva. Nel caso in cui la connessione sia inattiva oppure
     * sia chiusa, viene creata una nuova connessione utilizzando i parametri
     * definiti all'interno del metodo
     *
     *
     * @return la connessione al database
     * @throws SQLException se si verifica un errore durante la creazione o
     * l'accesso alla connessione
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        if (c == null || c.isClosed()) {
            c = DriverManager.getConnection(URL, USER, PASS);
        }
        return c;
    }
}
