package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;
import util.AlertManager;

/**
 *
 * @class AnalysisImpl
 * @brief Classe che rappresenta un'analisi di frequenza di parole.
 *
 * Questa classe memorizza una mappa di parole e il numero di occorrenze per la
 * singola parola. Permette di effettuare un analisi su un testo, contando la
 * frequenza delle parole e gestendo le stopwords(le parole da escludere
 * dall'analisi).
 *
 * La classe fornisce inoltre anche dei metodi per trovare la parola più
 * frequente all'interno del testo, per avere una parola casuale presente
 * all'interno del testo, e di unire due analisi al fine di ottenere statistiche
 * relative al testo analizzato.
 *
 *
 */
public class AnalysisImpl implements Serializable {

    /**
     * @brief Mappa che associa parole (chiavi) a frequenze (valori).
     *
     * Permette di tenere traccia delle parole trovate nel testo e della loro
     * frequenza.
     *
     */
    private HashMap<String, Integer> analysis;
    private int wordCounter = 0;

    private transient AlertManager alertManager = new AlertManager();

    /**
     * @brief Costruttore di default.
     *
     * Inizializza la mappa che conterrà l'analisi del testo.
     */
    public AnalysisImpl() {
        this.analysis = new HashMap<>();
    }

    /**
     * @brief Inserisce o aggiorna la frequenza di una parola all'interno della
     * mappa.
     *
     * In particolare nel caso in cui la parola sia già presente all'interno
     * della mappa, verrà aggiornata la frequenza.
     *
     *
     * @param k La parola (chiave) da inserire o aggiornare .
     * @param v La frequenza(valore) associata alla parola.
     */
    public void put(String k, Integer v) {
        analysis.put(k, v);
    }

    /**
     * @brief Restituisce la frequenza associata a una parola.
     * 
     * Nel caso in cui la parola non sia presente nella mappa verrà restituito
     * zero. 
     * 
     * @param k La parola di cui si vuole conoscere la frequenza.
     * @return La frequenza della parola, o 0 se non è presente nella mappa.
     */
    public Integer frequency(String k) {
        return analysis.getOrDefault(k, 0);
    }

    /**
     * @brief Restituisce la parola con la frequenza più alta.
     * @return La parola più frequente, oppure null se la mappa è vuota.
     */
    public String mostFrequent() {
        return analysis.entrySet().stream()
                .max(java.util.Map.Entry.comparingByValue())
                .map(java.util.Map.Entry::getKey)
                .orElse(null);
    }

    /**
     * @brief Restituisce una parola scelta casualmente dalla mappa (quindi una 
     * parola presente nel testo che si sta analizzando).
     * 
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

    /**
     * @brief Unisce due analisi, sommando la frequenza delle parole comuni
     * 
     * Le frequenze vengono sommate solo nel caso in cui la parola che stiamo
     * considerando è presente in entrambe le analisi
     * 
     * @param other un altro ogetto AnalysisImpl da unire all'analisi corrente.
     */
    public void mergeWith(AnalysisImpl other) {
        for (String word : other.analysis.keySet()) {
            int totalFreq = this.frequency(word) + other.frequency(word);
            this.put(word, totalFreq);
        }
    }

    /**
     * @brief Restituisce l'insieme delle parole analizzate.
     * @return Un Set contenente tutte le parole presenti nell'analisi.
     */
    public Set<String> keySet() {
        return analysis.keySet();
    }

    public int size() {
        return analysis.size();
    }

    /**
     * 
     * @return 
     */
    public String difficulty() {
        if (this.wordCounter <= 250) {
            return "facile";
        } else if (this.wordCounter > 250 && this.wordCounter <= 750) {
            return "medio";
        } else {
            return "difficile";
        }
    }

    /**
     * @brief Analizza un testo e popola la mappa con la frequenza delle parole,
     * escludendo le stopwords.
     *
     * @param text Il testo da analizzare.
     * @param stopwords Lista di parole da ignorare durante l'analisi (es.
     * articoli, congiunzioni, punteggiatura).
     */
    public int analyzeText(File file, Set<String> stopwords) {
        this.wordCounter = 0; // azzeramento del contatore
        List<String> lines = null;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("ERRORE nel metodo analyzeText di AnalysisImpl\n");
        }

        for (String line : lines) {
            String[] words = line.split("\\W+");
            for (String word : words) {
                if (!word.isEmpty() && !stopwords.contains(word.toLowerCase())) {
                    analysis.merge(word.toLowerCase(), 1, Integer::sum);
                    wordCounter++;
                }
            }
        }
        return this.wordCounter;
    }

}
