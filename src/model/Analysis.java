/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

/**
 *  
 * @class Analysis
 * @brief Classe che rappresenta un'analisi di frequenza di parole.
 *
 * Questa classe memorizza una mappa di parole e la loro frequenza di occorrenza.
 */
public class Analysis implements Serializable {
    /**
     * @brief Mappa che associa parole (chiavi) a frequenze (valori).
     */
    HashMap<String, Integer> analysis;

    /**
     * @brief Costruttore di default.
     *
     * Inizializza la mappa delle analisi.
     */
    public Analysis() {
        this.analysis = new HashMap<>();
    }

    /**
     * @brief Inserisce o aggiorna la frequenza di una parola.
     * @param k La parola (chiave).
     * @param v La frequenza associata alla parola.
     */
    public void put(String k, Integer v) {
        analysis.put(k, v);
    }

    /**
     * @brief Restituisce la frequenza associata a una parola.
     * @param k La parola di cui si vuole conoscere la frequenza.
     * @return La frequenza della parola, o 0 se non è presente nella mappa.
     */
    public Integer frequency(String k) {
        return analysis.getOrDefault(k, 0);
    }

    /**
     * @brief Restituisce una parola scelta casualmente dalla mappa.
     * @return Una parola casuale, oppure null se la mappa è vuota.
     */
    public String getRandom() {
        if (analysis.isEmpty()) {
            return null;
        }

        Set<String> keys = analysis.keySet();
        int randomIndex = new Random().nextInt(keys.size());

        return keys.stream().skip(randomIndex).findFirst().orElse(null);
    }
    public void mergeWith(Analysis other) {
        for (String word : other.analysis.keySet()) {
        int totalFreq = this.frequency(word) + other.frequency(word);
        this.put(word, totalFreq);
        }
    }

    int size() {
        return analysis.size();
    }
    
}
