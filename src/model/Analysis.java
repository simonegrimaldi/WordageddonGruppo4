/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package model;

import java.io.File;
import java.util.Set;

/**
 *
 * @author simonegrimaldi
 */
public interface Analysis {

    public void put(String k, Integer v);

    public int size();

    public Set<String> keySet();

    public Integer frequency(String k);

    public String mostFrequent();

    public String getRandom();

    public void mergeWith(AnalysisImpl other);

    public String difficulty();

    public boolean analyzeText(File file, Set<String> stopwords);

    public void saveAnalysis(AnalysisImpl a, String filename);

    public AnalysisImpl loadAnalysis(String filename);

}
