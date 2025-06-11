package dao.interfaces;

import java.util.LinkedHashMap;

/**
 * @interface DaoGame
 * @brief Interfaccia per l'implementazione del patter Data Acces Object,
 * associato all'entità Game.
 *
 * L'interfaccia definisce i metodi per interagire con la persistenza dei dati
 * relativi al gioco; nascondendo i dettagli implementativi di accesso al
 * database. I metodi dell'interfaccia permettono di inserire, recuperare e
 * aggiornare informazioni sul punteggio e sulle partite giocate.
 *
 * In particolar modo, la classe che implementa questa interfaccia si occupa di
 * eseguire le operazioni CRUD sul database.
 *
 * @see dao.implementation.DaoGameImpl
 *
 */
public interface DaoGame {

    /**
     * @brief Inserisce un nuovo punteggio all'interno del database.
     *
     * @param difficulty la difficoltà della partita
     * @param punteggio il punteggio ottenuto nell'ultima partita
     * @param username il nome utente dell'utente che ha giocata
     * @throws Exception se si verifica un errore durante l'inserimento dei dati
     * nel database
     */
    public void inserisci(String difficulty, int punteggio, String username) throws Exception;

    /**
     * @brief Recupera il punteggio più alto per un determinato utente.
     *
     * @param username il nome dell'utente di cui si desidera ottenere il
     * punteggio massimo
     * @return il punteggio più alto dell'utente, o -1 se l'utente non ha mai
     * giocato
     * @throws Exception se si verifica un errore di lettura dal database
     */
    public Integer getBestPointsPoints(String username) throws Exception;

    /**
     * @brief Recupera i primi 20 utenti presenti nel database con il punteggio
     * più alto.
     *
     * @return una mappa che contiene il nome dell'utente e il suo miglior
     * punteggio
     * @throws Exception, nel caso in cui si verifica un errore durante la
     * lettura dei dati dal database
     */
    public LinkedHashMap<String, Integer> getTopThree() throws Exception;

    /**
     * @brief Recupera il numero di partite giocate da un utente.
     *
     * @param username il nome dell'utente di cui si desidera ottenere il numero
     * di partite
     * @return il numero di partite giocate dall'utente
     * @throws Exception se si verifica un errore durante la lettura dei dati
     * dal database
     */
    public Integer getNumberGame(String username) throws Exception;

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
     */
    public Integer getLastMatch(String username) throws Exception;

    /**
     * @brief Recupera la media dei punteggi di un utente, nel caso in cui l'
     * utente non abbia mai giocato verrà restituito -1
     *
     * @param username il nome dell'utente di cui si desidera ottenere la media
     * dei punteggi
     * @return la media dei punteggi oppure -1 nel caso in cui l'utente non
     * abbia mai giocato
     * @throws Exception se si verifica un errore durante la lettura dei dati
     * dal database
     */
    public Double getAverageMatch(String username) throws Exception;

}
