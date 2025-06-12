package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import model.AnalysisImpl;

/**
 * @class DocumentQuestion
 * @brief Rappresenta una domanda di tipo "documento" in un quiz
 *
 * Questa classe genera una domanda basandosi su un documento casuale, chiedendo
 * quale sia la parola più frequente nel documento in questione. Viene
 * memorizzata la risposta corretta e vengono fornite quattro possibilità di
 * risposta (una di queste è quella corretta)
 */
public class DocumentQuestion extends Question<String> {

    /**
     * @brief Costruttore della domanda, seleziona una documento casuale e crea
     * la domanda.
     *
     * In particolare possiamo dire che il costruttore esegue i seguenti passi:
     * - Selezionta un documento casuale dalla lista delle analisi - Imposta la
     * domanda chiedendo quale sia la parola più frequente nel documento - Trova
     * la parola più frequente nel documento scelto - Crea quattro opzioni di
     * risposta, assicurandosi che la risposta corretta sia inclusa - Mischia le
     * opzioni di risposta
     *
     * @param analysis Lista che contiene le analisi di tutti i documenti
     * presenti da cui verrà selezionata un'analisi in modo casuale
     *
     */
    public DocumentQuestion(List<AnalysisImpl> analysis) {
        if (analysis == null || analysis.size() < 1) {
            throw new IllegalArgumentException("La lista di analisi è vuota.");
        }

        int numero = new Random().nextInt(analysis.size());
        AnalysisImpl selected = analysis.get(numero);

        this.question = "Qual è la parola più frequente nel documento numero " + (numero + 1) + " ?";

        this.answer = selected.mostFrequent();

        Set<String> selectedWords = new LinkedHashSet<>();
        selectedWords.add(this.answer);
        while (selectedWords.size() < 4) {
            selectedWords.add(selected.getRandom());
        }

        this.options = new ArrayList<>(selectedWords);
        Collections.shuffle(this.options);

    }

    /**
     * @brief Valuta la risposta dell'utente
     *
     * Il metodo valuta se la risposta selezionata è quella corretta. Se la
     * risposta è corretta vengono assegnati 10 punti, altrimenti ne vengono
     * detratti 3. Se la risposta viene lasciata vuota, il punteggio attribuito
     * sarà 0.
     *
     * @param selectedAnswer la risposta selezionata dall'utente
     * @return il punteggio ottenuto dall'utente alla singola domanda
     */
    @Override
    public int valuta(String selectedAnswer) {
        if (selectedAnswer == "") {
            return 0;
        }
        return answerQuestion(selectedAnswer) ? 10 : -3;
    }

}
