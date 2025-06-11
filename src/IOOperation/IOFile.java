package IOOperation;

import java.io.File;
import java.util.List;

/**
 * @interface IOFile
 *
 * @brief L'interfaccia definisce i metodi necessari per caricare e salvare un
 * file.
 *
 * Le classi che implementano questa interfaccia forniscono delle
 * implementazioni concrete per la gestione del caricamento e del salvataggio di
 * un file (che sia un file testuale o un file binario).
 */
public interface IOFile {

    /**
     * @brief Carica il contenuto di un file
     *
     * Consente di prelevare il contenuto di un file, che verrà restituito sotto
     * forma di stringa
     *
     * @param filename lista dei file da caricare
     * @return Il contenuto del file selezionato sotto forma di unica Stringa
     */
    public String loadFile(List<String> filenames);

    /**
     * @brief Salva il contenuto di un file in una destinazione specificata
     *
     * Il metodo permette di copiare il contenuto in un File in una destinazione
     * differente rispetto a quella del file da copiare. Viene restituito un
     * valore booleano che indica se l'operazione è stata effettuata con
     * successo o meno.
     *
     * @param destFile il file di destinazione dove il contenuto verrà copiato
     * @param selectedFile il file di origine da cui copiare il contenuto
     * @return 'true' se il file è stato salvato con successo, altrimenti
     * 'false'
     */
    public boolean saveFile(File destFile, File selectedFile);

}
