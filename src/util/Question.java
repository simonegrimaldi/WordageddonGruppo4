package util;
import java.util.List;

/**
 * @class Question
 * @brief Classe astratta che rappresenta una domanda con opzioni multiple.
 *
 * La classe generica `Question` fornisce una struttura di base per le domande del quiz. Ogni domanda
 * ha un testo, delle opzioni multiple e una risposta corretta. La classe è estesa da altre classi concrete 
 * che implementano diversi tipi di domande (es. domande a risposta multipla, domande di frequenza, ecc.).
 *
 * @param <T> Il tipo dei dati per la risposta e le opzioni (ad esempio, `String` per le risposte di testo, 
 * `Integer` per domande numeriche).
 */
public abstract class Question<T> {

    protected String question;
    protected List<T> options;
    protected T answer;

   /**
     * @brief Restituisce il testo della domanda.
     * 
     * @return La domanda come stringa.
     */
    public String getQuestionText() {
        return question;
    }


    /**
     * @brief Restituisce le opzioni di risposta.
     * 
     * @return La lista delle opzioni di risposta.
     */
    public List<T> getOptions() {
        return options;
    }

    /**
     * @brief Restituisce la risposta corretta.
     * 
     * @return La risposta corretta.
     */
    public T getAnswer() {
        return answer;
    }

    /**
     * @brief Restituisce una rappresentazione testuale della domanda con le opzioni.
     *
     * La rappresentazione include la domanda seguita dalle opzioni, ciascuna preceduta
     * da una lettera (A, B, C, ecc.). Le opzioni sono separate da una nuova riga.
     *
     * @return La rappresentazione della domanda con le opzioni come stringa.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(question).append("\n");

        char label = 'A';
        if (options == null) {
            System.out.println("ciaoooodi");
        }
        for (T option : options) {
            sb.append(label).append(". ").append(option).append("\n");
            label++;
        }

        return sb.toString();
    }

    /**
     * @brief Verifica se la risposta dell'utente è corretta.
     *
     * Confronta la risposta dell'utente con la risposta corretta memorizzata nella classe.
     * Restituisce `true` se la risposta è corretta, altrimenti `false`.
     *
     * @param userAnswer La risposta fornita dall'utente.
     * @return `true` se la risposta dell'utente è corretta, `false` altrimenti.
     */
    public boolean answerQuestion(T userAnswer) {
        return answer.equals(userAnswer);
    }
 
    /**
     * @brief Metodo astratto per valutare la risposta dell'utente.
     *
     * Questo metodo deve essere implementato dalle classi concrete per calcolare il punteggio
     * in base alla risposta dell'utente.
     * 
     * @param selectedAnswer La risposta selezionata dall'utente.
     * @return Il punteggio assegnato per la risposta (ad esempio, 10 per una risposta corretta, 
     * -3 per una risposta errata, ecc.).
     */
    public abstract int valuta(String selectedAnswer);
       

}
