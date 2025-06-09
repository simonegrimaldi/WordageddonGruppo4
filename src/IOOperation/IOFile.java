package IOOperation;

import java.io.File;

/**
 * @interface IOFile
 *
 * Questa interfaccia definisce i metodi necessari per caricare e salvare file.
 * Le classi che implementano questa interfaccia devono fornire
 * un'implementazione concreta per il caricamento e il salvataggio dei file,
 * come lettura da un file di testo o la gestione di file binari.
 */
public interface IOFile {

    /**
     * Carica il contenuto di un file
     *
     * @param filename, il nome del file da caricare
     * @return Il contenuto del file selezionato in formato String
     */
    public String loadFile(String filename);
    
    public boolean saveFile(File destFile,File selectedFile);

}
