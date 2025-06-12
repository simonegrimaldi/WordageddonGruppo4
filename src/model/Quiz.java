package model;

import util.Question;
import IOOperation.IOTextsImpl;
import java.util.List;

/**
 * @class Quiz
 * @brief Rappresenta un quiz contenente una serie di testi e una serie di
 * domande
 *
 * La classe viene utilizzata per gestire la sezione relativa ai quiz, gestisce
 * inoltre il punteggio ottenuto dall'utente, che viene calcolato in base al
 * numero di risposte fornite dal giocatore.
 *
 * Ogni Quiz è associato a un nome utente e ad una sequenza di testi e domande
 */
public class Quiz {

    private List<String> testi;
    private List<Question> domande;
    private int points = 0;
    private static int id = 0;
    private final String username;
    private IOTextsImpl text;

    /**
     * @brief Costruttore del quiz
     *
     * Il costruttore crea un oggetto Quiz, assegnando i testi, le domande e il
     * nome utente specificato. Ogni quiz ha un identificatore univoco che viene
     * incrementato automaticamente ogni volta che viene generato un quiz
     *
     * @param testi La lista di testi da leggere nel quiz
     * @param domande La lista di domande del quiz
     * @param username Il nome dell'utente che sta eseguendo il quiz
     *
     * @pre Testi e domande devono essere non nulli
     * @post Il quiz viene correttamente creato, associando testi, domande e
     * username del giocatore
     */
    public Quiz(List<String> testi, List<Question> domande, String username) {
        this.testi = testi;
        this.domande = domande;
        this.username = username;
        this.id++;
    }

    /**
     * @brief Restituisce il nome dell'utente associato all'oggetto Quiz
     * corrente
     *
     * @return Il nome utente
     */
    public String getUsername() {
        return username;
    }

    /**
     * @brief Restituisce la lista di testi associata al quiz
     *
     * @return La lista dei testi
     */
    public List<String> getTesti() {
        return testi;
    }

    /**
     * @brief Restituisce la lista delle domande associate al quiz
     *
     * @return La lista delle domande
     */
    public List<Question> getDomande() {
        return domande;
    }

    /**
     * @brief Imposta il punteggio del quiz in base alle risposte fornite dal
     * giocatore
     *
     * Il metodo confronta le risposte fornite dall'utente con le risposte
     * corrette nel quiz e calcola il punteggio totale. Ad ogni risposta
     * corretta il punteggio del giocatore viene incrementato.
     *
     * @param selectedAnswers La lista delle risposte selezionate dall'utente
     */
    public void setPoints(List<String> selectedAnswers) {

        for (int i = 0; i < domande.size(); i++) {
            Question q = domande.get(i);
            String selectedAnswer = selectedAnswers.get(i);
            
            this.points += q.valuta(selectedAnswer);
        }

    }

    /**
     * @brief Restituisce il punteggio finale del quiz
     * 
     * @return il punteggio calcolato in base alle risposte selezionate
     */
    public int getPoints() {
        return this.points;
    }
}
