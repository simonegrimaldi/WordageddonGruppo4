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
    

    /**
     * Crea una domanda del tipo:
     * "Quale tra le seguenti parole è la più frequente in tutti i documenti?"
     *
     * @param analyses Lista di oggetti Analysis da combinare.
     */
    public RelativeFrequencyQuestion(List<AnalysisImpl> analyses) {
        this.question = "Quale tra le seguenti parole è la più frequente in tutti i documenti?";
        AnalysisImpl total = new AnalysisImpl();
 
        // Merge di tutte le analisi
        for (AnalysisImpl a : analyses) {
            total.mergeWith(a);
        }
        System.out.println(total.keySet().toString());

        if (total.size() < 4) {
            throw new IllegalArgumentException("Non ci sono abbastanza parole per generare la domanda.");
        }

        // Seleziona 4 parole distinte a caso
        Set<String> selectedWords = new LinkedHashSet<>();
        int lastFreq = -1; // Inizializza con un valore impossibile
    
        while (selectedWords.size() < 4) {
            String candidate = total.getRandom();
            int candidateFreq = total.frequency(candidate);
        
        // Aggiungi solo se la frequenza è diversa dalla precedente
            if (candidateFreq != lastFreq) {
                selectedWords.add(candidate);
                lastFreq = candidateFreq;
            }
        }

        this.options = new ArrayList<>(selectedWords);
        
        // Trova quella con frequenza massima
        this.answer = this.options.stream()
            .max(Comparator.comparingInt(total::frequency))
            .orElse(null);

        
    }

    @Override
    public int valuta(String selectedAnswer) {
        if(selectedAnswer==null){
                return 0;
        }
        return answerQuestion(selectedAnswer) ? 10 : -3 ;
    }
    
}
