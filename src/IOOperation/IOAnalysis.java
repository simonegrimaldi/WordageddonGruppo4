package IOOperation;

import model.Analysis;

/**
 * @interface IOAnalysis
 *
 * @brief Interfaccia per la gestione delle operazioni di caricamento e
 * salvataggio di un'analisi
 *
 * Definisce i metodi necessari per caricare e salvare un file di tipo
 * 'Analysis'. Verrà implementata da classi come 'IOAnalysisImpl' che fornisce
 * dei metodi concreti per il caricamento e il salvataggio di un'analisi.
 *
 */
public interface IOAnalysis {

    /**
     * @brief Permette di caricare un'analisi da un file specificato
     *
     * Il metodo legge un file e restituisce un oggetto di tipo 'Analysis'.
     *
     * @param filename il nome del file da cui prelevare l'analisi
     * @return un oggetto di tipo 'Analysis' caricato dal file
     */
    public Analysis loadAnalysis(String filename);

    /**
     * @brief Permette di salvare un oggetto di tipo 'Analysis', su un file
     * specificato
     *
     * Il metodo scrive l'oggetto 'Analysis' su un file, dando quindi la
     * possibilità di persistere i dati ottenuti dall'analisi, in modo che
     * possano essere recuperati in seguito
     *
     * @param a l'oggetto di tipo 'Analysis' da salvare
     * @param filename il nome del file in cui andrà salvata l'analisi
     */
    public void saveAnalysis(Analysis a, String filename);
}
