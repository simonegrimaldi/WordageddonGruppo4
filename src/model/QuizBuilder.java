/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import IOOperation.IOAnalysis;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 *
 * @author corry
 */

public class QuizBuilder {
    private IOTexts textReader;
    private IOAnalysis analysisReader;
    private String username;
    
    public QuizBuilder(IOTexts textReader, IOAnalysis analysisReader,String username) {
        this.textReader = textReader;
        this.analysisReader = analysisReader;
        this.username = username;
    }

    public Quiz creaQuiz(String difficolta) {
        List<String> testi = textReader.cercaTesti(difficolta);
        List<AnalysisImpl> analisiList = new ArrayList<>();

        for (String nomeTesto : testi) {
            String pathAnalisi = nomeTesto.replace(".txt","Analysis.bin");
            AnalysisImpl a =  analysisReader.loadAnalysis(pathAnalisi);
            if (a != null) {
                analisiList.add(a);
            }
        }

        List<Question> domande = genera(analisiList);
        return new Quiz(testi, domande,username);
    }
    
    public List<Question> genera(List<AnalysisImpl> analisi) {
        List<Question> domande = new ArrayList<>();

        // Aggiunge una domanda di frequenza per il primo testo (se presente)
        if (!analisi.isEmpty()) {
            domande.add(new FrequencyQuestion(analisi.get(0)));
        }

        // Aggiunge una domanda di frequenza relativa se ci sono almeno 2 testi
        if (analisi.size() >= 2) {
            domande.add(new RelativeFrequencyQuestion(analisi));
        }

        // Aggiunge una domanda di esclusione se ci sono almeno 3 testi
        if (analisi.size() >= 3) {
           // List<Analysis> inclusi = List.of(analisi.get(0));
            List<AnalysisImpl> esclusi = analisi.subList(1, analisi.size());
            //domande.add(new ExclusionQuestion(inclusi, esclusi));
        }

        return domande;
    }
}