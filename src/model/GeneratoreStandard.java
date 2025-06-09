/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

public class GeneratoreStandard implements GeneratoreDomande {

    @Override
    public List<Question> genera(List<Analysis> analisi) {
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
            List<Analysis> esclusi = analisi.subList(1, analisi.size());
            //domande.add(new ExclusionQuestion(inclusi, esclusi));
        }

        return domande;
    }
}
