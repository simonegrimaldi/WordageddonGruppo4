/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import model.AnalysisImpl;

/**
 *
 * @author corry
 */
public class ExclusionQuestion extends Question<String>{
   

    public ExclusionQuestion(List<AnalysisImpl> analisiQuesti, List<AnalysisImpl> analisiAltri) {
        this.question = "Quale di queste parole non compare mai in NESSUN documento proposto?";
        //  Merge degli inclusi
        AnalysisImpl inclusi = new AnalysisImpl();
        for (AnalysisImpl a : analisiQuesti) {
            inclusi.mergeWith(a);
        }

        // Merge degli esclusi
        AnalysisImpl esclusi = new AnalysisImpl();
        for (AnalysisImpl a : analisiAltri) {
            esclusi.mergeWith(a);
        }

        // Trovaiamo parole che stanno solo negli esclusi
        Set<String> candidateAnswers = new HashSet<>(esclusi.keySet());
        candidateAnswers.removeAll(inclusi.keySet());

        if (candidateAnswers.size() < 1) {
            System.out.println("Nessuna parola valida per generare la domanda.");
            return;
        }

        // 4 Scegliamo una parola
        this.answer = candidateAnswers.stream().findAny().orElse(null);

        // distrattori
        
        Set<String> selectedWords = new LinkedHashSet<>();
        selectedWords.add(this.answer);
        while (selectedWords.size() < 4) {
             selectedWords.add(inclusi.getRandom());
        }
        
        

       this.options = new ArrayList<>(selectedWords);
       Collections.shuffle(this.options);  // mischiamo le opzioni

        
    }

    @Override
    public int valuta(String selectedAnswer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}


