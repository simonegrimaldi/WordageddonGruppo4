package dao.interfaces;

/**
 * @interface DaoUser
 * @brief Interfaccia per la gestione delle operazioni sugli utenti
 *
 * L'interfaccia definisce i metodi per gestire l'autenticazione e la
 * registrazione degli utenti nel database. I metodi di questa interfaccia
 * permettono di verificare le credenziali dell'utente, registrare nuovi utenti
 * e gestire le informazioni sugli utenti.
 *
 * L'interfaccia viene implementata da 'DaoUserImpl'
 *
 * @see dao.implementation.DaoUserImpl
 */
public interface DaoUser {

    /**
     * @brief Esegue l'autenticazione dell'utente.
     *
     * @param username il nome dell'utente da identificare
     * @param password la password inserita dall'utente
     * @return il tipo di utente se le credenziali sono corrette alrimenti null
     * @throws SQLException se si verifica un errore durante l'interazione con
     * il database
     */
    public String authentication(String username, String password);

    /**
     * @brief Registra un nuovo utente
     *
     * @param username l'username dell'utente che si vuole registrare
     * @param password la password dell'utente che si vuole registrare
     * @return 'true' se l'utente è stao registrato con successo, 'false' se
     * l'username esiste già
     * @throws SQLException se si verifica un errore durante l'inserimento nel
     * database
     */
    public boolean registration(String username, String password);
}
