package IOOperation;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import model.Analysis;
import model.AnalysisImpl;

/**
 * @class IOAnalysisImpl
 *
 * @brief La classe fornisce un implementazione concreta dell'interfaccia
 * 'IOAnalysis'
 *
 * In particolar modo la classe fornisce un'implementazione dei metodi per la
 * gestione del salvataggio e del caricamento di un oggetto di tipo 'Analysis'
 * su e da un file. Utilizzando il formato di serializzazione degli oggetti
 * viene quindi gestito questo meccanismo di input/output.
 *
 * @author User
 */
public class IOAnalysisImpl implements IOAnalysis {

    /**
     * @brief Salva l'analisi su un file specificato.
     *
     * Il metodo serializza un oggetto 'Analysis' e lo salva su un file
     * specificato. In caso di errore durante il salvataggio verrà stampato lo
     * stack trace relativo all'eccezione che viene sollevata
     *
     * @param a l'oggetto di tipo 'Analysis' da salvare
     * @param filename il nome del file in cui andrà salvata l'analisi
     */
    public void saveAnalysis(Analysis a, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(a);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @brief Carica un oggetto di tipo 'Analysis' da un file specificato
     *
     * Questo metodo deserializza un oggetto di tipo Analysis da un file
     * specificato Nel caso in cui il file non esista o non contenga un oggetto
     * valido verrà sollevata un eccezzione e verrà stampato lo stack trace
     * relativo all'eccezione sollevata
     *
     * @param filename il nome del file da cui prelevare l'analisi
     * @return un oggetto di tipo 'Analysis' caricato dal file
     */
    public AnalysisImpl loadAnalysis(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (AnalysisImpl) ois.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
