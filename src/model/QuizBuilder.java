/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

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

    public QuizBuilder(IOTexts textReader, IOAnalysis analysisReader) {
        this.textReader = textReader;
        this.analysisReader = analysisReader;
    }

    public Quiz creaQuiz(String difficolta, GeneratoreDomande generatore) {
        List<String> testi = textReader.cercaTesti(difficolta);
        List<Analysis> analisiList = new ArrayList<>();

        try {
            for (String nomeTesto : testi) {
                Analysis a = analysisReader.leggiAnalisi(nomeTesto);
                if (a != null) {
                    analisiList.add(a);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore lettura analisi: " + e.getMessage());
        }

        List<Question> domande = generatore.genera(analisiList);
        return new Quiz(testi, domande);
    }
}


