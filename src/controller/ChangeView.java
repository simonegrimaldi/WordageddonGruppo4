package controller;

/**
 * @interface ChangeView
 *
 * @brief Interfaccia per la gestione della navigazione tra le varie view
 * dell'applicazione
 *
 * L'interfaccia fornisce i metodi per la navigazione tra le schermate
 * dell'applicazione. Implementata dalla classe 'ChangeViewController', permette
 * ai controller delle diverse view di cambiare schermata senza dipendere da
 * implementazioni concrete, applicando così nel modopiù semplice ed efficace il
 * principio di inversione delle dipendenze.
 *
 */
public interface ChangeView {

    /**
     * @brief Permette di navigare alla schermata di navigazione
     *
     * Il metodo viene invocato quando l'utente vuole accedere alla schermata di
     * navigazione
     */
    public void goSignUp();

    /**
     * @brief Naviga verso la schermata di Login
     *
     * Il metodo viene invocato quando viene avviata l'applicazione, o quando
     * viene effettuato il Logout dall'applicazione
     */
    public void goLogIn();

    /**
     * @brief Naviga alla schermata principale per l'utente
     *
     * Il metodo è invocato per navigare alla HomePage.
     * @param username utilizzato per personalizzare la schermata di Home
     */
    public void goHome(String username);

    /**
     * @brief Naviga alla schermata di amministrazione
     *
     * Il metodo è invocato quando l'amministratore vuole navigare verso il
     * pannello di amministrazione
     *
     * @param superUsername utilizzato per personalizzare il pannello
     * amministratore
     *
     */
    public void goAdminPanel(String superUsername);

    /**
     * @brief Naviga verso la schermata di lettura
     *
     * Il metodo viene invocato quando si inizia il gioco, si passa quindi alla
     * lettura del testo, personalizzata in base alla difficoltà
     *
     * @param difficulty rappresenta il livello di difficoltà scelto dall'utente
     * per la lettura
     * @return 'true' se la schermata di lettura viene caricata correttamente,
     * 'false' altrimenti
     */
    public boolean goReading(String difficulty);

    /**
     * @brief Naviga verso la schermata contenente le domande
     *
     * Il metodo viene invocato quando l'utente ha completato la lettura del
     * testo o quando il tempo di lettura termina, l'utente viene qundi
     * reindirizzato alla schermata contenente le domande relative al testo
     * appena letto.
     */
    public void goQuestion();

    public void goEntry();
}
