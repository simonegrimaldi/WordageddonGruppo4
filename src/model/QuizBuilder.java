/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import util.Question;
import util.RelativeFrequencyQuestion;
import util.DocumentQuestion;
import util.FrequencyQuestion;
import IOOperation.IOTexts;
import IOOperation.IOAnalysis;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author corry
 */
public class QuizBuilder {

    private IOTexts textReader;
    private IOAnalysis analysisReader;
    private String username;

    public QuizBuilder(IOTexts textReader, IOAnalysis analysisReader, String username) {
        this.textReader = textReader;
        this.analysisReader = analysisReader;
        this.username = username;
    }

    public Quiz creaQuiz(String difficolta) {
        List<String> testi = textReader.cercaTesti(difficolta);
        if(testi == null)
                return null;
        List<AnalysisImpl> analisiList = new ArrayList<>();

        for (String nomeTesto : testi) {
            String pathAnalisi = nomeTesto.replace(".txt", "Analysis.bin");
            Analysis a = analysisReader.loadAnalysis(pathAnalisi);
            if (a != null) {
                analisiList.add((AnalysisImpl) a);
            }
        }

        List<Question> domande = genera(analisiList);
        return new Quiz(testi, domande, username);
    }

    public List<Question> genera(List<AnalysisImpl> analisi) {
        List<Question> domande = new ArrayList<>();

        // Aggiunge una domanda di frequenza per il primo testo (se presente)
        if (!analisi.isEmpty()) {
            domande.add(new FrequencyQuestion((Analysis) analisi.get(0)));
            domande.add(new FrequencyQuestion((Analysis) analisi.get(0)));
            domande.add(new RelativeFrequencyQuestion(analisi));
            domande.add(new RelativeFrequencyQuestion(analisi));
        }

        if (analisi.size() == 1) {
            domande.add(new RelativeFrequencyQuestion(analisi));
        } else {
            domande.add(new DocumentQuestion(analisi));
        }

        // Aggiunge una domanda di frequenza relativa se ci sono almeno 2 testi
        /* if (analisi.size() >= 2) {
            domande.add(new RelativeFrequencyQuestion(analisi));
        }*/

 /* Aggiunge una domanda di esclusione se ci sono almeno 3 testi
        if (analisi.size() >= 3) {
            List<Analysis> inclusi = Collecti.(analisi.get(0));
            List<AnalysisImpl> esclusi = analisi.subList(1, analisi.size());
            domande.add(new ExclusionQuestion(inclusi, esclusi));
        }*/
        return domande;
    }
}
