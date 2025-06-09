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

    public QuizBuilder(IOTexts textReader, IOAnalysis analysisReader) {
        this.textReader = textReader;
        this.analysisReader = analysisReader;
    }

    public Quiz creaQuiz(String difficolta, GeneratoreDomande generatore) {
        List<String> testi = textReader.cercaTesti(difficolta);
        List<AnalysisImpl> analisiList = new ArrayList<>();

        for (String nomeTesto : testi) {
            AnalysisImpl a =  analysisReader.loadAnalysis(nomeTesto);
            if (a != null) {
                analisiList.add(a);
            }
        }

        List<Question> domande = generatore.genera(analisiList);
        return new Quiz(testi, domande);
    }
}


