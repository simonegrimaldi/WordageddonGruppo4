package model;

import model.Analysis;
import java.io.IOException;

public interface IOAnalysis {
    /**
     * Carica l'oggetto Analysis da file serializzato corrispondente al testo indicato.
     * @param nomeTesto nome del file (es. "testo1.txt")
     */
    
    Analysis loadAnalysis(String filename) ;
    void saveAnalysis(Analysis a, String filename);
}
