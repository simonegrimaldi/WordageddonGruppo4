/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author corry
 */
public class DocumentQuestion extends Question<String>{
    /*Quale parola compare più spesso nel
n-esimo documento?*/
    
    
    public DocumentQuestion(List<AnalysisImpl> analysis) {
    if (analysis == null || analysis.size() < 1) {
        throw new IllegalArgumentException("La lista di analisi è vuota.");
    }

    int numero = new Random().nextInt(analysis.size());  // documento scelto casualmente
    AnalysisImpl selected = analysis.get(numero);            // analisi del documento selezionato

    this.question = "Qual è la parola più frequente nel documento numero " + (numero + 1);

    // Trova la parola più frequente
    this.answer = selected.mostFrequent();

    // Genera le opzioni
    Set<String> selectedWords = new LinkedHashSet<>();
    selectedWords.add(this.answer);
    while (selectedWords.size() < 4) {
            selectedWords.add(selected.getRandom()); // PER IMPEDIRE DUPLICATI
        }

       this.options = new ArrayList<>(selectedWords);
       Collections.shuffle(this.options);  // mischiamo le opzioni


    
}

    @Override
    public int valuta(String selectedAnswer) {
        if(selectedAnswer==null){
                return 0;
        }
        return answerQuestion(selectedAnswer) ? 10 : -3 ;
    }

    
}
