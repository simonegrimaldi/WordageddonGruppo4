package model;

import java.io.File;
import java.util.Set;

/**
 * @interface Analysis
 * @brief Interfaccia per l'analisi della frequenza delle parole in un testo
 *
 * L'interfaccia definisce i metodi necessari per eseguire l'analisi della
 * frequenza delle parole contenute in un file di testo, escludendo le
 * stopwords. Ogni implementazione di questa interfaccia deve fornire i metodi
 * per gestire la raccolta delle parole, calcolare la loro frequenza e
 * determinare le difficoltà del testo.
 *
 * L'interfaccia è implementata dalla classe 'AnalysisImpl'
 *
 * @see model.AnalysisImpl
 */
public interface Analysis {

    /**
     * @brief Inserisce o aggiorna la frequenza di una parola nella mappa delle
     * analisi
     *
     * Questo metodo aggiunge o aggiorna la frequenza di una parola all'interno
     * della mappa di analisi
     *
     * @param k rappresenta la parola da inserire
     * @param v rappresenta la frequenza associata alla parola
     *
     * @pre {@code k} e {@code v} devono essere validi
     * @post la parola {@code k} è stata inserita o aggiornata con la frequenza
     * {@code v}
     */
    public void put(String k, Integer v);

    /**
     * @brief Restituisce la dimensione della mappa di analisi
     *
     * @return il numero di parole analizzate
     */
    public int size();

    /**
     * @brief Metodo per il recupero dell'insieme delle parole analizzate
     *
     * @return un set di parole contenente le parole coinvolte nell'analisi
     */
    public Set<String> keySet();

    /**
     * @brief Restituisce la frequenza associata ad una parola analizzata
     *
     * @param k la parola di cui si desidera conoscere la frequenza
     * @return la frequenza della parola nell'analisi
     */
    public Integer frequency(String k);

    /**
     * @brief Restituisce la parola con la frequenza più alta all'interno
     * dell'analisi.
     *
     * @return la parola più frequente all'interno della mappa oppure null se
     * l'analisi è vuota.
     */
    public String mostFrequent();

    /**
     * @brief Restituisce una parola casuale presa dell'analisi
     *
     * @return Una parola casuale prelevata dall'analisi, o null se l'analisi è
     * vuota
     */
    public String getRandom();

    /**
     * @brief Unisce l'analisi di un oggetto con l'analisi corrente
     *
     * Il metodo fonde l'analisi di un altro oggetto con la mappa corrente
     * sommando le frequenze delle parole comuni
     *
     * @param other un'altro oggetto Analysis da unire all'analisi corrente
     */
    public void mergeWith(AnalysisImpl other);

    /**
     * @brief Calcola la difficoltà del testo in base al numero di parole
     * (escluse le stopwords). Questo metodo determina la difficoltà del testo
     * analizzato in base al numero di parole: - Facile: ≤ 250 parole - Medio:
     * da 250 a 750 parole - Difficile: > 750 parole
     * @return La difficoltà del testo ("facile", "medio" o "difficile")
     */
    public String difficulty();

    /**
     * @brief Analizza un testo e popola la mappa con la frequenza delle parole,
     * escludendo le stopwords. Questo metodo analizza il contenuto di un file
     * di testo, escludendo le parole presenti nella lista delle stopwords, e
     * popola la mappa con la frequenza delle parole restanti.
     *
     * @param file Il file da analizzare.
     * @param stopwords Un set contenente le parole da escludere durante
     * l'analisi.
     * @return Il numero di parole analizzate.
     *
     */
    public int analyzeText(File file, Set<String> stopwords);
}
