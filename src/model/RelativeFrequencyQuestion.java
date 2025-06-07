/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author corry
 */
public class RelativeFrequencyQuestion extends Question<String> {
    String question;
    String answer;
    List<String> options;

    /**
     * Crea una domanda del tipo:
     * "Quale tra le seguenti parole è la più frequente in tutti i documenti?"
     *
     * @param analyses Lista di oggetti Analysis da combinare.
     */
    public RelativeFrequencyQuestion(List<Analysis> analyses) {
        this.question = "Quale tra le seguenti parole è la più frequente in tutti i documenti?";
        Analysis total = new Analysis();

        // Merge di tutte le analisi
        for (Analysis a : analyses) {
            total.mergeWith(a);
        }

        if (total.size() < 4) {
            throw new IllegalArgumentException("Non ci sono abbastanza parole per generare la domanda.");
        }

        // Seleziona 4 parole distinte a caso
        Set<String> selectedWords = new LinkedHashSet<>();
        while (selectedWords.size() < 4) {
            selectedWords.add(total.getRandom()); // PER IMPEDIRE DUPLICATI
        }

        this.options = new ArrayList<>(selectedWords);

        // Trova quella con frequenza massima
        this.answer = this.options.stream()
            .max(Comparator.comparingInt(total::frequency))
            .orElse(null);

        
    }
}
