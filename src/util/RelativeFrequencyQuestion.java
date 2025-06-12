package util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.AnalysisImpl;

/**
 * @class RelativeFrequencyQuestion
 * @brief Rappresenta una domanda in cui l'utente deve identificare la parola
 * più frequente tra tutte quelle presenti in più documenti.
 *
 * Questa classe estende la classe `Question` e genera una domanda del tipo:
 * "Quale tra le seguenti parole è la più frequente in tutti i documenti?".
 *
 * La domanda viene generata utilizzando una lista di oggetti `AnalysisImpl` che
 * vengono combinati per calcolare la frequenza di tutte le parole presenti nei
 * documenti analizzati. La risposta corretta è la parola con la frequenza più
 * alta.
 */
public class RelativeFrequencyQuestion extends Question<String> {

    /**
     * Costruisce una domanda del tipo "Quale tra le seguenti parole è la più
     * frequente in tutti i documenti?". La domanda viene generata analizzando
     * una lista di oggetti `AnalysisImpl` e selezionando quattro parole con
     * frequenze differenti.
     *
     * @param analyses La lista di oggetti `AnalysisImpl` che rappresentano le
     * analisi dei documenti.
     * @throws IllegalArgumentException Se non ci sono abbastanza parole per
     * generare la domanda.
     */
    public RelativeFrequencyQuestion(List<AnalysisImpl> analyses) {
        this.question = "Quale tra le seguenti parole è comparsa più volte?";
        AnalysisImpl total = new AnalysisImpl();

        for (AnalysisImpl a : analyses) {
            total.mergeWith(a);
        }
        System.out.println(total.keySet().toString());

        if (total.size() < 4) {
            throw new IllegalArgumentException("Non ci sono abbastanza parole per generare la domanda.");
        }

        Set<String> selectedWords = new LinkedHashSet<>();
        int lastFreq = -1;

        while (selectedWords.size() < 4) {
            String candidate = total.getRandom();
            int candidateFreq = total.frequency(candidate);

            if (candidateFreq != lastFreq) {
                selectedWords.add(candidate);
                lastFreq = candidateFreq;
            }
        }

        this.options = new ArrayList<>(selectedWords);

        this.answer = this.options.stream()
                .max(Comparator.comparingInt(total::frequency))
                .orElse(null);

    }

    /**
     * @brief Valuta la risposta dell'utente.
     *
     * @param selectedAnswer La risposta selezionata dall'utente.
     * @return Il punteggio ottenuto per la risposta: - 10 se la risposta è
     * corretta - -3 se la risposta è errata
     */
    @Override
    public int valuta(String selectedAnswer) {
        if (selectedAnswer == "") {
            return 0;
        }
        return answerQuestion(selectedAnswer) ? 10 : -3;
    }

}
